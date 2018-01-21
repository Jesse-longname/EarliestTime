import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class EarliestTime {
	public static void main(String... args) {
		// Create input
		System.out.println("Test begin");
		System.out.println("Type in a number, or no input for a random input");
		Scanner s = new Scanner(System.in);
		String input = s.nextLine();
		LinkedList<Integer> nums = new LinkedList<>();
		if (input.equals("")) {
			Random r = new Random();
			for (int i = 0; i < 6; i++) {
				nums.add((int)(r.nextDouble() * 10));
			}
		} else if (input.length() != 6) {
			System.out.println("Invalid");
			s.close();
			return;
		} else {
			for (int i = 0; i < 6; i++) {
				nums.add(input.charAt(i) - '0');
			}
		}
		System.out.println(nums);
		System.out.println(solve(nums));
		System.out.println("Test finish");
		s.close();
	}
	
	static String solve(List<Integer> nums) {
		Collections.sort(nums);
		int[] output = new int[6];
		// Fill in hour
		output[0] = nums.remove(0);
		if (output[0] > 2) return "Invalid"; // No valid hour num
		else if (output[0] == 2) { // First hour num is 2, which is a special case
			output[1] = nums.remove(0);
			if (output[1] >= 4) { // Second num has the be <= 3
				return "Invalid";
			}
		}
		else { // First hour num is 1 or 0, no any other num can come next. Preferably lowest
			// See if there are enough nums to go to the first minute and second num
			int numsBelow6 = countBelowThreshold(nums, 6);
			if (numsBelow6 >= 3) { // Enough nums to use lowest in second hour num
				output[1] = nums.remove(0);
			} else { // Not enough nums, so use third lowest lowest
				output[1] = nums.remove(2);
			}
		}
		// Fill in minutes and seconds
		int numsBelow6 = countBelowThreshold(nums, 6);
		if (numsBelow6 < 2) return "Invalid";
		output[2] = nums.remove(0);
		numsBelow6--;
		if (numsBelow6 > 1) { // Enough nums lower than 6 to use in second minute position
			output[3] = nums.remove(0);
		} else { // The lowest num needs to be used for first seconds digit
			output[3] = nums.remove(1);
		}
		// At this point, the last numbers should just be filled in
		output[4] = nums.remove(0);
		output[5] = nums.remove(0);
		return String.format("%d%d:%d%d:%d%d", output[0], output[1], output[2], output[3], output[4], output[5]);
		
	}
	
	static int countBelowThreshold(List<Integer> nums, int thresh) {
		int count = 0;
		for (int num : nums) {
			if (num < thresh) count++;
		}
		return count;
	}
}
