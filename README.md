# file-tree
# Description: EPAM software development educational project
# Details


Implement the tree method in FileTreeImpl class.


An input parameter is a path.


If a given path does not exist, return an empty Optional.


If a given path refers to a file, return a String with its name and size like this:
some-file.txt 128 bytes


If a given path refers to a directory, return a String with its name, total size and its full hierarchy:
some-dir 100 bytes
├─ some-inner-dir 50 bytes
│  ├─ some-file.txt 20 bytes
│  └─ some-other-file.txt 30 bytes
└─ some-one-more-file.txt 50 bytes



Use pseudo-graphic characters to format the output.
Compute the size of a directory as a sum of all its contents.
Sort the contents in following way:

Directories should go first.
Directories and files are sorted in lexicographic order (case-insensitive).
