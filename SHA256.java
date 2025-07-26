import java.util.Arrays;

public class SHA256 {
    private static final int[] K = {
        0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,
        0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,
        0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,
        0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,
        0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,
        0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,
        0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,
        0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2
    };

    private final byte[] data = new byte[64];
    private int datalen = 0;
    private long bitlen = 0;
    private final int[] state = new int[8];

    public SHA256() {
        init();
    }

    private static int ROTRIGHT(int a, int b) {
        return (a >>> b) | (a << (32 - b));
    }

    private static int CH(int x, int y, int z) {
        return (x & y) ^ (~x & z);
    }

    private static int MAJ(int x, int y, int z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    private static int EP0(int x) {
        return ROTRIGHT(x,2) ^ ROTRIGHT(x,13) ^ ROTRIGHT(x,22);
    }

    private static int EP1(int x) {
        return ROTRIGHT(x,6) ^ ROTRIGHT(x,11) ^ ROTRIGHT(x,25);
    }

    private static int SIG0(int x) {
        return ROTRIGHT(x,7) ^ ROTRIGHT(x,18) ^ (x >>> 3);
    }

    private static int SIG1(int x) {
        return ROTRIGHT(x,17) ^ ROTRIGHT(x,19) ^ (x >>> 10);
    }

    public void init() {
        datalen = 0;
        bitlen = 0;
        state[0] = 0x6a09e667;
        state[1] = 0xbb67ae85;
        state[2] = 0x3c6ef372;
        state[3] = 0xa54ff53a;
        state[4] = 0x510e527f;
        state[5] = 0x9b05688c;
        state[6] = 0x1f83d9ab;
        state[7] = 0x5be0cd19;
    }

    private void transform(byte[] data) {
        int[] m = new int[64];
        int a, b, c, d, e, f, g, h, t1, t2;

        for (int i = 0, j = 0; i < 16; ++i, j += 4)
            m[i] = ((data[j] & 0xff) << 24) | ((data[j + 1] & 0xff) << 16) |
                   ((data[j + 2] & 0xff) << 8) | (data[j + 3] & 0xff);
        for (int i = 16; i < 64; ++i)
            m[i] = SIG1(m[i - 2]) + m[i - 7] + SIG0(m[i - 15]) + m[i - 16];

        a = state[0];
        b = state[1];
        c = state[2];
        d = state[3];
        e = state[4];
        f = state[5];
        g = state[6];
        h = state[7];

        for (int i = 0; i < 64; ++i) {
            t1 = h + EP1(e) + CH(e, f, g) + K[i] + m[i];
            t2 = EP0(a) + MAJ(a, b, c);
            h = g;
            g = f;
            f = e;
            e = d + t1;
            d = c;
            c = b;
            b = a;
            a = t1 + t2;
        }

        state[0] += a;
        state[1] += b;
        state[2] += c;
        state[3] += d;
        state[4] += e;
        state[5] += f;
        state[6] += g;
        state[7] += h;
    }

    public void update(byte[] input, int len) {
        for (int i = 0; i < len; ++i) {
            data[datalen] = input[i];
            datalen++;
            if (datalen == 64) {
                transform(data);
                bitlen += 512;
                datalen = 0;
            }
        }
    }

    public byte[] digest() {
        byte[] hash = new byte[32];
        int i = datalen;

        // Pad whatever data is left in the buffer.
        if (datalen < 56) {
            data[i++] = (byte)0x80;
            while (i < 56)
                data[i++] = 0x00;
        } else {
            data[i++] = (byte)0x80;
            while (i < 64)
                data[i++] = 0x00;
            transform(data);
            Arrays.fill(data, 0, 56, (byte)0);
        }

        // Append to the padding the total message's length in bits and transform.
        bitlen += datalen * 8;
        data[63] = (byte)(bitlen);
        data[62] = (byte)(bitlen >>> 8);
        data[61] = (byte)(bitlen >>> 16);
        data[60] = (byte)(bitlen >>> 24);
        data[59] = (byte)(bitlen >>> 32);
        data[58] = (byte)(bitlen >>> 40);
        data[57] = (byte)(bitlen >>> 48);
        data[56] = (byte)(bitlen >>> 56);
        transform(data);

        // Since SHA uses big endian, convert int state to bytes in big endian order
        for (i = 0; i < 4; ++i) {
            hash[i]      = (byte)((state[0] >>> (24 - i * 8)) & 0xff);
            hash[i + 4]  = (byte)((state[1] >>> (24 - i * 8)) & 0xff);
            hash[i + 8]  = (byte)((state[2] >>> (24 - i * 8)) & 0xff);
            hash[i + 12] = (byte)((state[3] >>> (24 - i * 8)) & 0xff);
            hash[i + 16] = (byte)((state[4] >>> (24 - i * 8)) & 0xff);
            hash[i + 20] = (byte)((state[5] >>> (24 - i * 8)) & 0xff);
            hash[i + 24] = (byte)((state[6] >>> (24 - i * 8)) & 0xff);
            hash[i + 28] = (byte)((state[7] >>> (24 - i * 8)) & 0xff);
        }
        return hash;
    }

    // Convenience method for hashing a byte array
    public static byte[] hash(byte[] input) {
        SHA256 sha = new SHA256();
        sha.update(input, input.length);
        return sha.digest();
    }
} 