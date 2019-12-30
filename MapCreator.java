import java.util.*;
import java.io.*;

public class MapCreator{
	public static void main(String[] args) throws IOException{
		Random gen = new Random();
		Scanner input = new Scanner(System.in);
		
			PrintWriter print = new PrintWriter(args[0], "UTF-8");
			
			System.out.print("Enter the size (length=width) of square map: ");
			int n = input.nextInt();
			print.println(n);
			for (int i = 0; i<n; i++){
				for (int j = 0; j < n; j++){
					print.print(gen.nextInt(2) + " ");
				}
				print.println();
			}
			print.close();
		
	}
}