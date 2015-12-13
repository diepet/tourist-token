package io.tourist.token.listener;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.CameraRoll;
import io.tourist.core.api.Shot;
import io.tourist.core.api.Tour;
import io.tourist.token.extractor.SelfTokenExtractor;
import io.tourist.token.model.MutableInteger;
import io.tourist.token.model.TokenCounterNode;
import io.tourist.token.writer.TokenCounterNodeWriter;

public class TokenCounterTourEventListenerTests {
	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private ProceedingJoinPoint proceedingJoinPointDummyMethod;

	@Mock
	private Signature signatureDummyMethod;

	@Mock
	private Tour tourDummyMethod;

	@Mock
	private CameraRoll cameraRollDummyMethod;

	@Mock
	private ProceedingJoinPoint proceedingJoinPointInnerDummyMethod;

	@Mock
	private Signature signatureInnerDummyMethod;

	@Mock
	private Tour tourInnerDummyMethod;

	@Mock
	private CameraRoll cameraRollInnerDummyMethod;

	@Mock
	private ProceedingJoinPoint proceedingJoinPointInnerInnerDummyMethod;

	@Mock
	private Signature signatureInnerInnerDummyMethod;

	@Mock
	private Tour tourInnerInnerDummyMethod;

	@Mock
	private CameraRoll cameraRollInnerInnerDummyMethod;

	@Mock
	private Shot firstShot;

	@Mock
	private Shot secondShot;

	@Mock
	private Shot thirdShot;

	@Mock
	private Shot fourthShot;

	@Mock
	private Shot fifthShot;

	@Mock
	private Shot sixthShot;

	@Mock
	private Shot seventhShot;

	@Mock
	private Shot nullShot;

	@Mock
	private TokenCounterNodeWriter nodeWriter;

	private TokenCounterNode rootNode;

	private Capture<TokenCounterNode> captureRootNode;

	@Before
	public void setUp() {
		this.initMocks();
	}

	@After
	public void tearDown() {
		this.rootNode = null;
		this.captureRootNode = null;
	}

	@Test
	public void testDummyMethod() {
		TokenCounterTourEventListener eventListener = new TokenCounterTourEventListener();
		eventListener.setTokenExtractor(new SelfTokenExtractor());
		eventListener.setTokenCounterNodeWriter(this.nodeWriter);
		eventListener.onTouristTravelStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourDummyMethod);
		eventListener.onTourEnded(this.tourDummyMethod);
		eventListener.onTouristTravelEnded(this.tourDummyMethod);
		Assert.assertNotNull(this.rootNode);
		Assert.assertNull(this.rootNode.getChildren());
		Assert.assertNotNull(this.rootNode.getTokenCountedMap());
		Assert.assertEquals(2, this.rootNode.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(2), this.rootNode.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(1), this.rootNode.getTokenCountedMap().get("INSERT"));
	}

	@Test
	public void testInnerDummyMethod() {
		TokenCounterTourEventListener eventListener = new TokenCounterTourEventListener();
		eventListener.setTokenExtractor(new SelfTokenExtractor());
		eventListener.setTokenCounterNodeWriter(this.nodeWriter);
		eventListener.onTouristTravelStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourInnerDummyMethod);
		eventListener.onTourEnded(this.tourInnerDummyMethod);
		eventListener.onTourEnded(this.tourDummyMethod);
		eventListener.onTouristTravelEnded(this.tourDummyMethod);
		Assert.assertNotNull(this.rootNode);
		Assert.assertNotNull(this.rootNode.getTokenCountedMap());
		Assert.assertEquals(2, this.rootNode.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(3), this.rootNode.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(2), this.rootNode.getTokenCountedMap().get("INSERT"));
		Assert.assertNotNull(this.rootNode.getChildren());
		Assert.assertEquals(1, this.rootNode.getChildren().size());
		Assert.assertNotNull(this.rootNode.getChildren().get(0).getTokenCountedMap());
		Assert.assertEquals(2, this.rootNode.getChildren().get(0).getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(1),
				this.rootNode.getChildren().get(0).getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(1),
				this.rootNode.getChildren().get(0).getTokenCountedMap().get("INSERT"));
		Assert.assertNull(this.rootNode.getChildren().get(0).getChildren());
	}

	@Test
	public void testInnerInnerDummyMethod() {
		TokenCounterTourEventListener eventListener = new TokenCounterTourEventListener();
		eventListener.setTokenExtractor(new SelfTokenExtractor());
		eventListener.setTokenCounterNodeWriter(this.nodeWriter);
		eventListener.onTouristTravelStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourInnerDummyMethod);
		eventListener.onTourStarted(this.tourInnerInnerDummyMethod);
		eventListener.onTourEnded(this.tourInnerInnerDummyMethod);
		eventListener.onTourEnded(this.tourInnerDummyMethod);
		eventListener.onTourEnded(this.tourDummyMethod);
		eventListener.onTouristTravelEnded(this.tourDummyMethod);
		Assert.assertNotNull(this.rootNode);
		Assert.assertNotNull(this.rootNode.getTokenCountedMap());
		Assert.assertEquals(3, this.rootNode.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(4), this.rootNode.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(2), this.rootNode.getTokenCountedMap().get("INSERT"));
		Assert.assertEquals(new MutableInteger(1), this.rootNode.getTokenCountedMap().get("UPDATE"));
		Assert.assertNotNull(this.rootNode.getChildren());
		Assert.assertEquals(1, this.rootNode.getChildren().size());
		// assert first child
		TokenCounterNode node = this.rootNode.getChildren().get(0);
		Assert.assertNotNull(node.getTokenCountedMap());
		Assert.assertEquals(3, node.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(2), node.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(1), node.getTokenCountedMap().get("INSERT"));
		Assert.assertEquals(new MutableInteger(1), this.rootNode.getTokenCountedMap().get("UPDATE"));
		Assert.assertNotNull(node.getChildren());
		Assert.assertEquals(1, node.getChildren().size());
		// assert first child of the first child
		node = node.getChildren().get(0);
		Assert.assertNotNull(node.getTokenCountedMap());
		Assert.assertEquals(2, node.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(1), node.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(1), this.rootNode.getTokenCountedMap().get("UPDATE"));
		Assert.assertNull(node.getChildren());
	}

	@Test
	public void testInnerDummyMethodCalledTwoTimes() {
		TokenCounterTourEventListener eventListener = new TokenCounterTourEventListener();
		eventListener.setTokenExtractor(new SelfTokenExtractor());
		eventListener.setTokenCounterNodeWriter(this.nodeWriter);
		eventListener.onTouristTravelStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourInnerDummyMethod);
		eventListener.onTourEnded(this.tourInnerDummyMethod);
		eventListener.onTourStarted(this.tourInnerDummyMethod);
		eventListener.onTourEnded(this.tourInnerDummyMethod);
		eventListener.onTourEnded(this.tourDummyMethod);
		eventListener.onTouristTravelEnded(this.tourDummyMethod);
		// assert root
		Assert.assertNotNull(this.rootNode);
		Assert.assertNotNull(this.rootNode.getTokenCountedMap());
		Assert.assertEquals(2, this.rootNode.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(4), this.rootNode.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(3), this.rootNode.getTokenCountedMap().get("INSERT"));
		Assert.assertNotNull(this.rootNode.getChildren());
		Assert.assertEquals(2, this.rootNode.getChildren().size());
		// assert first child
		TokenCounterNode node = this.rootNode.getChildren().get(0);
		Assert.assertNotNull(node.getTokenCountedMap());
		Assert.assertEquals(2, node.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(1), node.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(1), node.getTokenCountedMap().get("INSERT"));
		Assert.assertNull(node.getChildren());
		// assert second child
		node = this.rootNode.getChildren().get(1);
		Assert.assertNotNull(node.getTokenCountedMap());
		Assert.assertEquals(2, node.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(1), node.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(1), node.getTokenCountedMap().get("INSERT"));
		Assert.assertNull(node.getChildren());
	}

	@Test
	public void testDummyMethodWithFailure() {
		TokenCounterTourEventListener eventListener = new TokenCounterTourEventListener();
		eventListener.setTokenExtractor(new SelfTokenExtractor());
		eventListener.setTokenCounterNodeWriter(this.nodeWriter);
		eventListener.onTouristTravelStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourDummyMethod);
		eventListener.onTourFailed(this.tourDummyMethod);
		eventListener.onTouristTravelEnded(this.tourDummyMethod);
		Assert.assertNotNull(this.rootNode);
		Assert.assertNull(this.rootNode.getChildren());
		Assert.assertNotNull(this.rootNode.getTokenCountedMap());
		Assert.assertEquals(0, this.rootNode.getTokenCountedMap().size());
	}

	@Test
	public void testInnerDummyMethodWithFailure() {
		TokenCounterTourEventListener eventListener = new TokenCounterTourEventListener();
		eventListener.setTokenExtractor(new SelfTokenExtractor());
		eventListener.setTokenCounterNodeWriter(this.nodeWriter);
		eventListener.onTouristTravelStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourDummyMethod);
		eventListener.onTourStarted(this.tourInnerDummyMethod);
		eventListener.onTourFailed(this.tourInnerDummyMethod);
		eventListener.onTourEnded(this.tourDummyMethod);
		eventListener.onTouristTravelEnded(this.tourDummyMethod);
		Assert.assertNotNull(this.rootNode);
		Assert.assertNotNull(this.rootNode.getTokenCountedMap());
		Assert.assertEquals(2, this.rootNode.getTokenCountedMap().size());
		Assert.assertEquals(new MutableInteger(2), this.rootNode.getTokenCountedMap().get("SELECT"));
		Assert.assertEquals(new MutableInteger(1), this.rootNode.getTokenCountedMap().get("INSERT"));
		Assert.assertNotNull(this.rootNode.getChildren());
		Assert.assertEquals(1, this.rootNode.getChildren().size());
		Assert.assertNotNull(this.rootNode.getChildren().get(0).getTokenCountedMap());
		Assert.assertEquals(0, this.rootNode.getChildren().get(0).getTokenCountedMap().size());
		Assert.assertNull(this.rootNode.getChildren().get(0).getChildren());
	}

	private void initMocks() {

		List<Shot> shotListDummyMethod = new LinkedList<Shot>();
		shotListDummyMethod.add(this.firstShot);
		shotListDummyMethod.add(this.secondShot);
		shotListDummyMethod.add(this.thirdShot);
		List<Shot> shotListInnerDummyMethod = new LinkedList<Shot>();
		shotListInnerDummyMethod.add(this.fourthShot);
		shotListInnerDummyMethod.add(this.fifthShot);
		List<Shot> shotListInnerInnerDummyMethod = new LinkedList<Shot>();
		shotListInnerInnerDummyMethod.add(this.sixthShot);
		shotListInnerInnerDummyMethod.add(this.seventhShot);
		shotListInnerInnerDummyMethod.add(this.nullShot);

		EasyMock.reset(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod,
				this.cameraRollDummyMethod, this.proceedingJoinPointInnerDummyMethod, this.signatureInnerDummyMethod,
				this.tourInnerDummyMethod, this.cameraRollInnerDummyMethod,
				this.proceedingJoinPointInnerInnerDummyMethod, this.signatureInnerInnerDummyMethod,
				this.tourInnerInnerDummyMethod, this.cameraRollInnerInnerDummyMethod, this.firstShot, this.secondShot,
				this.thirdShot, this.fourthShot, this.fifthShot, this.sixthShot, this.seventhShot, this.nullShot,
				this.nodeWriter);
		// dummy method
		EasyMock.expect(this.proceedingJoinPointDummyMethod.getSignature()).andReturn(this.signatureDummyMethod)
				.anyTimes();
		EasyMock.expect(this.signatureDummyMethod.getName()).andReturn("dummyMethod").anyTimes();
		EasyMock.expect(this.tourDummyMethod.getProceedingJoinPoint()).andReturn(this.proceedingJoinPointDummyMethod)
				.anyTimes();
		EasyMock.expect(this.tourDummyMethod.getFailCause()).andReturn(new RuntimeException()).anyTimes();
		EasyMock.expect(this.tourDummyMethod.getCameraRoll()).andReturn(this.cameraRollDummyMethod).anyTimes();
		EasyMock.expect(this.cameraRollDummyMethod.getShotList()).andReturn(shotListDummyMethod).anyTimes();
		// inner dummy method
		EasyMock.expect(this.proceedingJoinPointInnerDummyMethod.getSignature())
				.andReturn(this.signatureInnerDummyMethod).anyTimes();
		EasyMock.expect(this.signatureInnerDummyMethod.getName()).andReturn("innerDummyMethod").anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getProceedingJoinPoint())
				.andReturn(this.proceedingJoinPointInnerDummyMethod).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getFailCause()).andReturn(new IOException()).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getCameraRoll()).andReturn(this.cameraRollInnerDummyMethod)
				.anyTimes();
		EasyMock.expect(this.cameraRollInnerDummyMethod.getShotList()).andReturn(shotListInnerDummyMethod).anyTimes();
		// inner inner dummy method
		EasyMock.expect(this.proceedingJoinPointInnerInnerDummyMethod.getSignature())
				.andReturn(this.signatureInnerInnerDummyMethod).anyTimes();
		EasyMock.expect(this.signatureInnerInnerDummyMethod.getName()).andReturn("innerInnerDummyMethod").anyTimes();
		EasyMock.expect(this.tourInnerInnerDummyMethod.getProceedingJoinPoint())
				.andReturn(this.proceedingJoinPointInnerInnerDummyMethod).anyTimes();
		EasyMock.expect(this.tourInnerInnerDummyMethod.getFailCause()).andReturn(new SocketException()).anyTimes();
		EasyMock.expect(this.tourInnerInnerDummyMethod.getCameraRoll()).andReturn(this.cameraRollInnerInnerDummyMethod)
				.anyTimes();
		EasyMock.expect(this.cameraRollInnerInnerDummyMethod.getShotList()).andReturn(shotListInnerInnerDummyMethod)
				.anyTimes();
		// shots
		EasyMock.expect(this.firstShot.getPicture()).andReturn("SELECT").anyTimes();
		EasyMock.expect(this.secondShot.getPicture()).andReturn("SELECT").anyTimes();
		EasyMock.expect(this.thirdShot.getPicture()).andReturn("INSERT").anyTimes();
		EasyMock.expect(this.fourthShot.getPicture()).andReturn("INSERT").anyTimes();
		EasyMock.expect(this.fifthShot.getPicture()).andReturn("SELECT").anyTimes();
		EasyMock.expect(this.sixthShot.getPicture()).andReturn("UPDATE").anyTimes();
		EasyMock.expect(this.seventhShot.getPicture()).andReturn("SELECT").anyTimes();
		EasyMock.expect(this.nullShot.getPicture()).andReturn(null).anyTimes();
		// node writer
		this.captureRootNode = EasyMock.newCapture();
		this.nodeWriter.write(EasyMock.capture(this.captureRootNode));
		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			@Override
			public TokenCounterNode answer() throws Throwable {
				rootNode = captureRootNode.getValue();
				return null;
			}
		}).anyTimes();
		EasyMock.replay(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod,
				this.cameraRollDummyMethod, this.proceedingJoinPointInnerDummyMethod, this.signatureInnerDummyMethod,
				this.tourInnerDummyMethod, this.cameraRollInnerDummyMethod,
				this.proceedingJoinPointInnerInnerDummyMethod, this.signatureInnerInnerDummyMethod,
				this.tourInnerInnerDummyMethod, this.cameraRollInnerInnerDummyMethod, this.firstShot, this.secondShot,
				this.thirdShot, this.fourthShot, this.fifthShot, this.sixthShot, this.seventhShot, this.nullShot,
				this.nodeWriter);
	}
}
