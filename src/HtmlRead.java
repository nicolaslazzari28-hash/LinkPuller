import java.net.*;
import java.io.*;
import java.util.Scanner;

public class HtmlRead {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String runAgain = "y";

        // Keep running until the user types something other than y
        while (runAgain.equals("y")) {
            System.out.print("Enter a link: ");
            String pageLink = input.nextLine();

            System.out.print("Enter a word to filter the links: ");
            String filterWord = input.nextLine();

            HtmlRead app = new HtmlRead(pageLink, filterWord);

            System.out.print("Run again? (y/n): ");
            runAgain = input.nextLine();
        }
    }
    public HtmlRead(String pageLink, String filterWord){

        try{
            System.out.println();
            URL pageUrl = new URL(pageLink);
            URLConnection connection = pageUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; " + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String htmlLine;
            int linkCount = 0;

            // Read the page one line at a time
            while ((htmlLine = reader.readLine()) != null) {

                // Find the first link in the line
                int startIndex = htmlLine.indexOf("http");

                // Keep going until there are no more links in the line
                while (startIndex != -1) {

                    // Find where the link ends
                    int endIndex = startIndex;
                    while (endIndex < htmlLine.length() && "\"' <>".indexOf(htmlLine.charAt(endIndex)) == -1) {
                        endIndex++;
                    }

                    // Cut the link out of the line and fix &amp;
                    String foundLink = htmlLine.substring(startIndex, endIndex).replace("&amp;", "&");

                    // Only print real links that have the filter word
                    if ((foundLink.startsWith("http://") || foundLink.startsWith("https://")) && foundLink.contains(filterWord)) {
                        System.out.println(foundLink);
                        linkCount++;
                    }

                    // Look for the next link after this one
                    startIndex = htmlLine.indexOf("http", endIndex);
                }
            }
            reader.close();

            // Show how many links were printed
            System.out.println(linkCount + " links found");
        } catch(Exception ex){
            System.out.println(ex);
        }
    }



}