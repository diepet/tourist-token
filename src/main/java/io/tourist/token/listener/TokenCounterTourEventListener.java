package io.tourist.token.listener;

import io.tourist.core.api.Tour;
import io.tourist.core.event.TourEventListenerAdapter;
import io.tourist.token.extractor.TokenExtractor;

public final class TokenCounterTourEventListener extends TourEventListenerAdapter {

	private TokenExtractor tokenExtractor;

	@Override
	public void onTouristTravelStarted(final Tour tour) {

	}

	@Override
	public void onTourStarted(final Tour tour) {

	}

	@Override
	public void onTourEnded(final Tour tour) {

	}

	@Override
	public void onTourFailed(final Tour tour) {

	}

	@Override
	public void onTouristTravelEnded(final Tour tour) {

	}

}
