package huffman;

class HuffmanNode extends HuffmanTree {
    final HuffmanTree left, right;
 
    HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}