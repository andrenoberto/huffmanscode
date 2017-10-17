# Huffman's Algorithm

This is an implementation of Huffman's Algorithm.
This implementation using Java uses RegEx to easily find blocks of code that'll be encoded.

# Instructions

# First step

- Clone this repo

`git clone https://github.com/andrenoberto/huffmanscode.git`

# Second step

- Open the local clone of this repo
- Compile Java binaries using javac and the following params

`javac -d out -sourcepath src src/huffman/HuffmanCode.java `


# Third step

- Go to generated production's folder

Unix systems:

`cd out/production/Huffman/`

Windows:

`cd out\production\Huffman\`

# Last step

- Run the binaries using java and passing the correct params
-- First param is the text file you're gonna encode
-- Second param is where the encoded output file will be stored

Example:

`java huffman.HuffmanCode path/to/TextFileToEncode.txt path/to/OutputBinaryFile.bin`

# Just To Know

You'll see that'll print the read text then the binary code and last it'll read the encoded file and print the decoded result.

If you want to check the encoded binary just check the *.bin file.


Any bug fixes and improvements are pretty welcome.
