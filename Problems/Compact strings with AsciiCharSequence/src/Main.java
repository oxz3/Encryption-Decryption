import java.util.Arrays;

class AsciiCharSequence  implements java.lang.CharSequence {
    // implementation
    private byte[] seq;

    AsciiCharSequence (byte[] seq) {
        this.seq = seq;
    }

    @Override
    public int length() {
        return seq.length;
    }

    @Override
    public char charAt(int i) {
        return (char) seq[i];
    }

    @Override
    public CharSequence subSequence(int a, int b) {
        return new AsciiCharSequence(Arrays.copyOfRange(seq, a, b));
    }

    @Override
    public String toString() {
        return new String(seq);
        
    }
}
