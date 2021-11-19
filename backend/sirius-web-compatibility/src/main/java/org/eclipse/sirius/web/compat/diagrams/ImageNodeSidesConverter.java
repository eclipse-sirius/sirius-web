package org.eclipse.sirius.web.compat.diagrams;

import com.google.common.collect.Lists;

import java.util.List;

import org.eclipse.sirius.diagram.description.style.Side;
import org.eclipse.sirius.web.diagrams.ImageNodeSide;

/**
 * Used to convert Sirius image node forbidden sides
 * to Sirius Web image node authorized sides.
 *
 * @author Koi-tsk
 */
public class ImageNodeSidesConverter {

    public List<ImageNodeSide> getAuthorizedSides(List<Side> forbiddenSides) {
        List<ImageNodeSide> portSides = Lists.newArrayList(ImageNodeSide.NORTH, ImageNodeSide.EAST, ImageNodeSide.SOUTH, ImageNodeSide.WEST);
        if (forbiddenSides.isEmpty()) {
            return List.of(ImageNodeSide.UNDEFINED);
        }
        for (Side side : forbiddenSides) {
            portSides.remove(ImageNodeSide.valueOf(side.getName()));
        }
        return portSides;
    }

}
