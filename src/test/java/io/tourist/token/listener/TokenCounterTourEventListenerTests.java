package io.tourist.token.listener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.CameraRoll;
import io.tourist.core.api.Shot;
import io.tourist.core.api.Tour;
import io.tourist.core.event.ShotPrinterTourEventListener;

public class TokenCounterTourEventListenerTests {
	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private ProceedingJoinPoint proceedingJoinPointDummyMethod;

	@Mock
	private Signature signatureDummyMethod;

	@Mock
	private Tour tourInnerDummyMethod;

	@Mock
	private CameraRoll cameraRollInnerDummyMethod;

	@Mock
	private ProceedingJoinPoint proceedingJoinPointInnerDummyMethod;

	@Mock
	private Signature signatureInnerDummyMethod;

	@Mock
	private Tour tourDummyMethod;

	@Mock
	private CameraRoll cameraRollDummyMethod;

	@Mock
	private Shot firstShot;

	@Mock
	private Shot secondShot;

	@Mock
	private Shot thirdShot;

	@Mock
	private OutputStream outputStream;

	@Before
	public void setUp() {
		this.initMocks();
	}

	@Test
	public void testSingleDummyMethod() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ShotPrinterTourEventListener shotPrinterTourEventListener = new ShotPrinterTourEventListener(baos);
		shotPrinterTourEventListener.onTouristTravelStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourEnded(this.tourDummyMethod);
		shotPrinterTourEventListener.onTouristTravelEnded(this.tourDummyMethod);
		String[] lines = baos.toString().split(String.format("%n"));
		Assert.assertEquals(5, lines.length);
		Assert.assertEquals("-- START TRAVEL --", lines[0]);
		Assert.assertEquals("dummyMethod():", lines[1]);
		Assert.assertEquals("\tSHOT #1: first shot", lines[2]);
		Assert.assertEquals("\tSHOT #2: second shot", lines[3]);
		Assert.assertEquals("-- END TRAVEL --", lines[4]);
	}

	@Test
	public void testFailedSingleDummyMethod() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ShotPrinterTourEventListener shotPrinterTourEventListener = new ShotPrinterTourEventListener(baos);
		shotPrinterTourEventListener.onTouristTravelStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourFailed(this.tourDummyMethod);
		shotPrinterTourEventListener.onTouristTravelEnded(this.tourDummyMethod);
		String[] lines = baos.toString().split(String.format("%n"));
		Assert.assertEquals(4, lines.length);
		Assert.assertEquals("-- START TRAVEL --", lines[0]);
		Assert.assertEquals("dummyMethod():", lines[1]);
		Assert.assertEquals("\tEXCEPTION THROWN: java.lang.RuntimeException", lines[2]);
		Assert.assertEquals("-- END TRAVEL --", lines[3]);
	}

	@Test
	public void testInnerDummyMethod() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ShotPrinterTourEventListener shotPrinterTourEventListener = new ShotPrinterTourEventListener(baos);
		shotPrinterTourEventListener.onTouristTravelStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourStarted(this.tourInnerDummyMethod);
		shotPrinterTourEventListener.onTourEnded(this.tourInnerDummyMethod);
		shotPrinterTourEventListener.onTourEnded(this.tourDummyMethod);
		shotPrinterTourEventListener.onTouristTravelEnded(this.tourDummyMethod);
		String[] lines = baos.toString().split(String.format("%n"));
		Assert.assertEquals(7, lines.length);
		Assert.assertEquals("-- START TRAVEL --", lines[0]);
		Assert.assertEquals("dummyMethod():", lines[1]);
		Assert.assertEquals("\tinnerDummyMethod():", lines[2]);
		Assert.assertEquals("\t\tSHOT #1: third shot", lines[3]);
		Assert.assertEquals("\tSHOT #1: first shot", lines[4]);
		Assert.assertEquals("\tSHOT #2: second shot", lines[5]);
		Assert.assertEquals("-- END TRAVEL --", lines[6]);
	}

	@Test
	public void testFailedInnerDummyMethod() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ShotPrinterTourEventListener shotPrinterTourEventListener = new ShotPrinterTourEventListener(baos);
		shotPrinterTourEventListener.onTouristTravelStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourStarted(this.tourDummyMethod);
		shotPrinterTourEventListener.onTourStarted(this.tourInnerDummyMethod);
		shotPrinterTourEventListener.onTourFailed(this.tourInnerDummyMethod);
		shotPrinterTourEventListener.onTourEnded(this.tourDummyMethod);
		shotPrinterTourEventListener.onTouristTravelEnded(this.tourDummyMethod);
		String[] lines = baos.toString().split(String.format("%n"));
		Assert.assertEquals(7, lines.length);
		Assert.assertEquals("-- START TRAVEL --", lines[0]);
		Assert.assertEquals("dummyMethod():", lines[1]);
		Assert.assertEquals("\tinnerDummyMethod():", lines[2]);
		Assert.assertEquals("\t\tEXCEPTION THROWN: java.io.IOException", lines[3]);
		Assert.assertEquals("\tSHOT #1: first shot", lines[4]);
		Assert.assertEquals("\tSHOT #2: second shot", lines[5]);
		Assert.assertEquals("-- END TRAVEL --", lines[6]);
	}

	private void initMocks() {

		List<Shot> shotListDummyMethod = new LinkedList<Shot>();
		shotListDummyMethod.add(this.firstShot);
		shotListDummyMethod.add(this.secondShot);
		List<Shot> shotListInnerDummyMethod = new LinkedList<Shot>();
		shotListInnerDummyMethod.add(this.thirdShot);

		EasyMock.reset(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod,
				this.cameraRollDummyMethod, this.proceedingJoinPointInnerDummyMethod, this.signatureInnerDummyMethod,
				this.tourInnerDummyMethod, this.cameraRollInnerDummyMethod, this.firstShot, this.secondShot,
				this.thirdShot);
		EasyMock.expect(this.proceedingJoinPointDummyMethod.getSignature()).andReturn(this.signatureDummyMethod)
				.anyTimes();
		EasyMock.expect(this.proceedingJoinPointInnerDummyMethod.getSignature())
				.andReturn(this.signatureInnerDummyMethod).anyTimes();
		EasyMock.expect(this.signatureDummyMethod.getName()).andReturn("dummyMethod").anyTimes();
		EasyMock.expect(this.signatureInnerDummyMethod.getName()).andReturn("innerDummyMethod").anyTimes();
		EasyMock.expect(this.tourDummyMethod.getProceedingJoinPoint()).andReturn(this.proceedingJoinPointDummyMethod)
				.anyTimes();
		EasyMock.expect(this.tourDummyMethod.getFailCause()).andReturn(new RuntimeException()).anyTimes();
		EasyMock.expect(this.tourDummyMethod.getCameraRoll()).andReturn(this.cameraRollDummyMethod).anyTimes();
		EasyMock.expect(this.cameraRollDummyMethod.getShotList()).andReturn(shotListDummyMethod).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getProceedingJoinPoint())
				.andReturn(this.proceedingJoinPointInnerDummyMethod).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getFailCause()).andReturn(new IOException()).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getCameraRoll()).andReturn(this.cameraRollInnerDummyMethod)
				.anyTimes();
		EasyMock.expect(this.cameraRollInnerDummyMethod.getShotList()).andReturn(shotListInnerDummyMethod).anyTimes();

		EasyMock.expect(this.firstShot.getPicture()).andReturn("first shot").anyTimes();
		EasyMock.expect(this.secondShot.getPicture()).andReturn("second shot").anyTimes();
		EasyMock.expect(this.thirdShot.getPicture()).andReturn("third shot").anyTimes();
		EasyMock.replay(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod,
				this.cameraRollDummyMethod, this.proceedingJoinPointInnerDummyMethod, this.signatureInnerDummyMethod,
				this.tourInnerDummyMethod, this.cameraRollInnerDummyMethod, this.firstShot, this.secondShot,
				this.thirdShot);
	}

	@Test
	public void testOutputStreamIOExceptionNotPropagated() throws IOException {
		EasyMock.reset(this.outputStream);
		this.outputStream.write((byte[]) EasyMock.anyObject());
		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			@Override
			public Object answer() throws Throwable {
				throw new IOException("Some IO exception");
			}
		});
		EasyMock.replay(this.outputStream);
		try {
			ShotPrinterTourEventListener shotPrinterTourEventListener = new ShotPrinterTourEventListener(
					this.outputStream);
			shotPrinterTourEventListener.onTouristTravelStarted(this.tourDummyMethod);
			shotPrinterTourEventListener.onTourStarted(this.tourDummyMethod);
			shotPrinterTourEventListener.onTourEnded(this.tourDummyMethod);
			shotPrinterTourEventListener.onTouristTravelEnded(this.tourDummyMethod);
		} catch (Throwable exceptionCaught) {
			Assert.fail("Unexpected exception thrown");
		}
	}

}
