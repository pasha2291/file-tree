package com.efimchick.ifmo.io.filetree;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FileTreeImpl implements FileTree {

	@Override
	public Optional<String> tree(Path path) {
		File file;
		StringBuilder result;
		if (path != null) {
			file = new File(path.toString());
		} else {
			return Optional.empty();
		}

		if (!file.exists()) {
			return Optional.empty();
		} else if (file.isFile()) {
			result = new StringBuilder();
			result.append(file.getName())
				  .append(" ")
				  .append(file.length())
				  .append(" bytes");
			return Optional.of(result.toString());
		} else {
			 int indent = 0;
			 result = new StringBuilder();
			 List<Integer> emptyLines = new ArrayList<>();
			 printDirectoryTree(path.toFile(), indent, result, false, emptyLines);
			 return Optional.of(result.toString());
		} 

	}
	
	private void printDirectoryTree(File folder, int indent,
	        StringBuilder result, boolean isLastDirectory, List<Integer> emptyLines) {
		
		File[] files = folder.listFiles();
		sortArray(files);
		StringBuilder file = new StringBuilder();

		result.append(getIndent(indent, false, isLastDirectory, emptyLines));
		result.append(printDirectory(folder));
	    
	    for (int i = 0; i < files.length; i++) {
	    	if (files[i].isDirectory()) {
	    		boolean isLastDir = isLastDirectory(folder, i) && file.length() == 0
	    				&& isLastFile(folder, i);
	    		if(isLastDir) {
	    			emptyLines.add(indent);
	    		}
	            printDirectoryTree(files[i], indent + 1, result,
	            		isLastDir, emptyLines);
	        } else {
	            file.append(printFile(files[i], indent + 1,
	            		isLastFile(folder, i), emptyLines));
	        }
	    	if(isLastDirectory(folder, i) && file.length() == 0
    				&& isLastFile(folder, i)) {
	    			emptyLines.remove(Integer.valueOf(indent));
	    	}
	    }
	    result.append(file);
	}
	
	private String getIndent(int indent, boolean isLastFile,
			boolean isLastDirectory, List<Integer>emptyLines) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < indent; i++) {
			if (i == indent - 1 && !isLastFile && !isLastDirectory) {
				result.append("├─ ");
			} else if ((i == indent - 1) && (isLastFile || isLastDirectory)) {
				result.append("└─ ");
			} else if(emptyLines.contains(i)) {
				result.append("   ");
			} else if(i < indent - 1) {
				result.append("│  ");
			}
		}
		return result.toString();
	}

	private String printFile(File file, int indent, boolean isLast, 
			List<Integer>emptyLines) {
		StringBuilder files = new StringBuilder();
		files.append(getIndent(indent, isLast, false, emptyLines));
	    files.append(file.getName())
	    	  .append(" ")
	    	  .append(file.length())
	    	  .append(" bytes\n");
	    return files.toString();
	}
	
	private String printDirectory(File folder) {
		StringBuilder header = new StringBuilder();
		header.append(folder.getName())
  	  		  .append(" ")
  	  		  .append(folderSize(folder))
  	  		  .append(" bytes\n");
		return header.toString();
	}
	
	private long folderSize(File directory) {
	    long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}
	
	private boolean isLastDirectory(File folder, int index) {
		boolean result = true;
		File[] files = folder.listFiles();
		sortArray(files);
		if(index + 1 > files.length - 1) {
			return result;
		}
		for (int i = index + 1; i < files.length; i++) {
			if(files[i].isDirectory()) {
				result = false;
			}
		}
		return result;
	}
	
	private boolean isLastFile(File folder, int index) {
		boolean result = true;
		File[] files = folder.listFiles();
		sortArray(files);
		if(index + 1 > files.length - 1) {
			return result;
		}
		for (int i = index + 1; i < files.length; i++) {
			if(files[i].isFile()) {
				result = false;
			}
		}
		return result;
	}
	
	private void sortArray(File[] files) {
		Arrays.sort(files, Comparator.comparing(File::getName,
				String.CASE_INSENSITIVE_ORDER));
	}
	
}
