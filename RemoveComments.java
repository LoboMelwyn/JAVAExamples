import java.io.*;

public class RemoveComments {
	public void readAndPrintFile(String fileName) {
		int ch;
		String changed="";
		@SuppressWarnings("unused")
		boolean tokenCheck = false;
		boolean slashCommentFound = false;
		boolean starCommentFound = false;
		boolean firstSlashFound = false;
		boolean firstStarFound = false;
		boolean closingStarFound = false;
		boolean startDoubleQuoteFound = false;
		int lastChar;

		try {

			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			System.out.println("Reading the file "+fileName);
			while ((ch = reader.read()) != -1) {

				lastChar = ch;

				if (ch == '\"') {
					if (startDoubleQuoteFound == false) {
						startDoubleQuoteFound = true;
					} else if (startDoubleQuoteFound == true) {
						startDoubleQuoteFound = false;
					}
				}

				if (startDoubleQuoteFound
						&& (starCommentFound == true || slashCommentFound == true)) {
					continue;
				}
				if (ch == '/') {
					if (starCommentFound == true && closingStarFound == false) {
						continue;
					}
					if (closingStarFound && starCommentFound == true) {
						starCommentFound = false;
						closingStarFound = false;
						firstStarFound = false;
						continue;
					} else if (firstSlashFound && slashCommentFound == false
							&& starCommentFound == false) {
						slashCommentFound = true;
						firstSlashFound = false;
						continue;
					} else if (slashCommentFound == false
							&& starCommentFound == false
							&& startDoubleQuoteFound == false) {
						firstSlashFound = true;
						continue;
					}
				}
				if (ch == '*') {
					if (starCommentFound) {
						closingStarFound = true;
						continue;
					}
					if (firstSlashFound && starCommentFound == false) {
						starCommentFound = true;
						firstSlashFound = false;
						continue;
					} else if (firstStarFound == false
							&& starCommentFound == true) {
						firstStarFound = true;
						continue;
					}
				}
				if (ch == '\n') {
					if (slashCommentFound) {
						slashCommentFound = false;
						firstStarFound = false;
						firstSlashFound = false;
						starCommentFound = false;
						System.out.print((char) ch);
						continue;
					}
				}

				if (starCommentFound == true && closingStarFound == false) {
					continue;
				}

				if (ch != '/' && ch != '*') {
					if (closingStarFound) {
						System.out.print((char) lastChar);
					}

					closingStarFound = false;
					firstSlashFound = false;
					firstStarFound = false;
					closingStarFound = false;

				}

				if (slashCommentFound == false && starCommentFound == false) {
					changed+=(char)ch;
				}
			}
			reader.close();
			Writer writeFile=new FileWriter(fileName+"(Copy)");
			System.out.println("Writing to the file "+fileName+"(copy)");
			writeFile.write(changed);
			System.out.println("Job Done .........");
			writeFile.flush();
			writeFile.close();

		} catch (FileNotFoundException ex) {
			System.out.println(fileName + " not found");
		} catch (Exception ex) {
			System.out.println("Error reading file " + fileName);
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RemoveComments reader = new RemoveComments();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the file name: ");
		String file;
		try {
			file = br.readLine();
			reader.readAndPrintFile(file);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
}
