import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smarty.space.user.mapper.AppointmentMapper;
import com.smarty.space.user.mapper.BookMapper;
import com.smarty.space.user.model.Appointment;
import com.smarty.space.user.model.Book;



public class Testjava  extends BaseTest{
	@Autowired
	private BookMapper bookMapper ;
	
	@Autowired
	private AppointmentMapper applicationMapper ;
	
	@Test
	public void testQueryById() throws Exception {
		
		long bookId = 1000;
		Book book = bookMapper.queryById(bookId);
		System.out.println(book);
	}
	
	@Test
	public void test() throws Exception {
		List<Book> lists = bookMapper.queryAll();
		for (Book book : lists) {
			System.out.println(book);
		}
	}
	
	@Test
	public void test1() throws Exception {
		Appointment appointment = new Appointment();
		appointment.setBookid((long) 2000);
		appointment.setStudentid((long) 201340430);
		Integer resultInteger = applicationMapper.insertAppointment(appointment);
		System.out.println(resultInteger);
	}
	
	

}
