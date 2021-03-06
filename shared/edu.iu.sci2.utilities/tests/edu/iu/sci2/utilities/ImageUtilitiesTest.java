package edu.iu.sci2.utilities;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iu.sci2.utilities.ImageUtilities;

public class ImageUtilitiesTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateBufferedImageFilledWithColor() {
		try {
			BufferedImage image =
				ImageUtilities.createBufferedImageFilledWithColor(Color.DARK_GRAY);
		}
		catch (Exception e) {
			fail();
		}
	}
}
