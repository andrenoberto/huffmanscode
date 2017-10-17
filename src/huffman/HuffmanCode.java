package huffman;

import java.io.IOException;
import java.util.PriorityQueue;
import rle.Main;
import RegEx.RegEx;

import static java.lang.System.out;
import static java.lang.System.setOut;
import static rle.Main.readFile;
import static rle.Main.writeFile;

public class HuffmanCode {

    public static void main(String[] args) {

        String test = readFile(args[0]);

        int[] charFreqs = new int[256];

        for (char c : test.toCharArray()) {
            charFreqs[c]++;
        }

        HuffmanTree tree = buildTree(charFreqs);
        String encode = encode(tree, test);

        String padrao = "([\\S\\d]{1,}?)\\1+";
        RegEx regEx = new RegEx(encode, padrao);

        encode = regEx.regExToERL();

        try {
            writeFile(args[1], encode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        test = readFile(args[1]);
        test = regEx.rleDecompress(test);
        System.out.println(decode(tree, test));
    }

    private static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
        for (int i = 0; i < charFreqs.length; i++) {
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char) i));
        }
        while (trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            trees.offer(new HuffmanNode(a, b));
        }

        return trees.poll();
    }

    private static String encode(HuffmanTree tree, String encode) {
        assert tree != null;

        StringBuilder encodeText = new StringBuilder();
        for (char c : encode.toCharArray()) {
            encodeText.append(getCodes(tree, new StringBuffer(), c));
        }
        return encodeText.toString();
    }


    private static String decode(HuffmanTree tree, String encode) {
        assert tree != null;

        StringBuilder decodeText = new StringBuilder();
        HuffmanNode node = (HuffmanNode) tree;
        for (char code : encode.toCharArray()) {
            if (code == '0') {
                if (node.left instanceof HuffmanLeaf) {
                    decodeText.append(((HuffmanLeaf) node.left).value);
                    node = (HuffmanNode) tree;
                } else {
                    node = (HuffmanNode) node.left;
                }
            } else if (code == '1') {
                if (node.right instanceof HuffmanLeaf) {
                    decodeText.append(((HuffmanLeaf) node.right).value);
                    node = (HuffmanNode) tree;
                } else {
                    node = (HuffmanNode) node.right;
                }
            }
        }
        return decodeText.toString();
    }

    private static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;

        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;


            out.println(leaf.value + "\t" + leaf.frequency + "\t\t" + prefix);

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;


            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length() - 1);


            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private static String getCodes(HuffmanTree tree, StringBuffer prefix, char w) {
        assert tree != null;

        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;


            if (leaf.value == w) {
                return prefix.toString();
            }

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;


            prefix.append('0');
            String left = getCodes(node.left, prefix, w);
            prefix.deleteCharAt(prefix.length() - 1);


            prefix.append('1');
            String right = getCodes(node.right, prefix, w);
            prefix.deleteCharAt(prefix.length() - 1);

            if (left == null) return right;
            else return left;
        }
        return null;
    }

}