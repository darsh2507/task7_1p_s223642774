package sit707_week7;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class BodyTemperatureMonitorTest {
	
	@Mock
	private TemperatureSensor temperatureSensorMock;
	@Mock
	private CloudService cloudServiceMock;
	@Mock
	private NotificationSender notificationSenderMock;
	
	private BodyTemperatureMonitor bodyTemperatureMonitor;
	
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		bodyTemperatureMonitor = new BodyTemperatureMonitor(temperatureSensorMock, cloudServiceMock, notificationSenderMock);
	}

	@Test
	public void testStudentIdentity() {
		String studentId = "s223642774";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Darsh Patel";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
	public void testReadTemperatureNegative() {
		
	when(temperatureSensorMock.readTemperatureValue()).thenReturn(-5.0);
	double temperature = bodyTemperatureMonitor.readTemperature();
	Assert.assertEquals(-5.0, temperature,0.01);;
	}
	
	@Test
	public void testReadTemperatureZero() {
		when(temperatureSensorMock.readTemperatureValue()).thenReturn(0.0);
		double temperature = bodyTemperatureMonitor.readTemperature();
		Assert.assertEquals(0.0, temperature,0.1);
	}
	
	@Test
	public void testReadTemperatureNormal() {
		when(temperatureSensorMock.readTemperatureValue()).thenReturn(36.5);
		double temperature = bodyTemperatureMonitor.readTemperature();
		Assert.assertEquals(36.5, temperature,0.01);	
	}

	@Test
	public void testReadTemperatureAbnormallyHigh() {
	when(temperatureSensorMock.readTemperatureValue()).thenReturn(40.0);
	double temperature = bodyTemperatureMonitor.readTemperature();
	Assert.assertEquals(40.0, temperature,0.01);
	}

	/*
	 * CREDIT or above level students, Remove comments. 
	 */
	@Test
	public void testReportTemperatureReadingToCloud() {
		// Mock reportTemperatureReadingToCloud() calls cloudService.sendTemperatureToCloud()
		TemperatureReading temperatureReading = mock(TemperatureReading.class);
	    bodyTemperatureMonitor.reportTemperatureReadingToCloud(temperatureReading);
	    verify(cloudServiceMock, times(1)).sendTemperatureToCloud(temperatureReading);
	}
		
	/*
	 * CREDIT or above level students, Remove comments. 
	 */
	@Test
	public void testInquireBodyStatusNormalNotification() {  
	when(cloudServiceMock.queryCustomerBodyStatus(bodyTemperatureMonitor.getFixedCustomer())).thenReturn("Normal");
	bodyTemperatureMonitor.inquireBodyStatus();
	verify(notificationSenderMock, times(1)).sendEmailNotification(eq(bodyTemperatureMonitor.getFixedCustomer()),anyString());
	}
//	
//	/*
//	 * CREDIT or above level students, Remove comments. 
//	 */
	@Test
	public void testInquireBodyStatusAbnormalNotification() {
		 when(cloudServiceMock.queryCustomerBodyStatus(bodyTemperatureMonitor.getFixedCustomer())).thenReturn("Abnormal");
		 bodyTemperatureMonitor.inquireBodyStatus();
		 verify(notificationSenderMock,times(1)).sendEmailNotification(eq(bodyTemperatureMonitor.getFamilyDoctor()),anyString());
	}
}
