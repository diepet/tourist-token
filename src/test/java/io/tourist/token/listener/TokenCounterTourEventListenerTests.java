package io.tourist.token.listener;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.CameraRoll;
import io.tourist.core.api.Shot;
import io.tourist.core.api.Tour;

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

	@Before
	public void setUp() {
		this.initMocks();
	}

	@Test
	public void testSingleDummyMethod() {

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
}
