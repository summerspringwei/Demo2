package sqlDao;

public class Course {

	public int weekDay=1;
	public int courseId=1;
	public String courseName=null;
	public String teacherName=null;
	public String location=null;
	
	public Course(int w,int c,String cn,String tn,String l){
		this.weekDay=w;
		this.courseId=c;
		this.courseName=cn;
		this.teacherName=tn;
		this.location=l;
	}
	
	@Override
	public String toString(){
		String s=weekDay+" "+courseId+" "+courseName+" "+teacherName+" "+location;
		return s;
	}
	public int getOrder(){
		return weekDay*7+courseId;
	}
	
}
