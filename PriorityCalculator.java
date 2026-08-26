package Blessing;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // It represents standard units of time
public class PriorityCalculator {
	// this is a method for waiting time
	 public static long calculateWaitingTimeDays(LocalDate reportedDate, LocalDate currentDate) {
		 long waitingTimeDays = ChronoUnit.DAYS.between(reportedDate, currentDate);
		 
		 if (waitingTimeDays < 0) {
			 System.out.println("Reported date cannot be in the future");
		 }
		 
		 return waitingTimeDays;
	 }
	 
	 public static boolean isOverdue(long waitingTimeDays, int overdueThresholdDays) {
		 
		 return waitingTimeDays > overdueThresholdDays;
		 
	 }
	 
	 public static double calculateWaitingTimeScore(long waitingTimeDays, int overdueThresholdDays) {
		 double score = ((double) waitingTimeDays / overdueThresholdDays) * 10;
		 
		 return Math.min(score, 10);
	 }
	 
	 public static String getPriorityLevel(double priorityScore) {
		 
		 if (priorityScore >= 80) {
			 return "Critical";
			 
		 } else if (priorityScore >= 60) { 
			 return "High";
			 
		 } else if (priorityScore >= 30) {
			 return "Medium";
			 
		 } else {
			 return "Low";
		 }
		 
	 }
	public static void main(String[] args) {
		// example with the starting reported Date
		LocalDate reportedDate = LocalDate.of(2026, 8, 18); // as of today
		LocalDate currentDate = LocalDate.now();
		int overdueThreshold = 10;
		
		long  waitingTime = calculateWaitingTimeDays(reportedDate, currentDate);
		boolean overdue = isOverdue(waitingTime, overdueThreshold);
		double waitingTimeScore = calculateWaitingTimeScore(waitingTime, overdueThreshold);
		
		System.out.println("Reported on: " + reportedDate);
		System.out.println("Waiting time: " + waitingTime + " days");
		System.out.println("Overdue: " + overdue);
		System.out.println("Waiting time score: " + waitingTimeScore);
	}
}
