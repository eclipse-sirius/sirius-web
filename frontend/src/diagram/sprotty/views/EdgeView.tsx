/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
/** @jsx svg */
import { svg } from 'snabbdom-jsx';
import { PolylineEdgeView, toDegrees } from 'sprotty';

/**
 * The view used to display edges.
 *
 * @sbegaudeau
 */
export class EdgeView extends PolylineEdgeView {
  /**
   * Renders the line of the edge with a specific style configured.
   * @param edge The edge
   * @param segments The segments
   * @param context The context
   */
  // @ts-ignore
  renderLine(edge, segments, context) {
    const { style } = edge;
    const styleObject = {
      stroke: style.color,
      'stroke-width': style.size,
      'pointer-events': 'stroke',
      fill: 'transparent',
    };

    if (style.lineStyle === 'Dash') {
      styleObject['stroke-dasharray'] = '5,5';
    } else if (style.lineStyle === 'Dot') {
      styleObject['stroke-dasharray'] = '2,2';
    } else if (style.lineStyle === 'Dash_Dot') {
      styleObject['stroke-dasharray'] = '10,5,2,2,2,5';
    }

    this.applyFeedback(edge, styleObject);

    const firstPoint = segments[0];
    let path = `M ${firstPoint.x},${firstPoint.y}`;
    for (let i = 1; i < segments.length; i++) {
      const p = segments[i];
      path += ` L ${p.x},${p.y}`;
    }

    // This pseudo-edge follows the same path as the main one,
    // but is 5 pixels wider on both sides and clickable everywhere
    // even if the edge uses dashes. It is always invisible but offers
    // an easier to target clickable area to select an edge.
    const selectionHelperStyleObject = {
      ...styleObject,
      'stroke-width': Math.max(1, style.size) + 10,
      'stroke-dasharray': 0,
      visibility: 'hidden',
    };
    const selectionHelper = (
      // @ts-ignore
      <path class-sprotty-edge={true} class-selection-helper={true} d={path} style={selectionHelperStyleObject} />
    );

    const dataTestid = edge.children[0]?.text ?? edge.id;
    return (
      <g attrs-data-testid={`Edge - ${dataTestid}`}>
        <path class-sprotty-edge={true} d={path} style={styleObject} />
        {selectionHelper}
      </g>
    );
  }

  /**
   * Customize the style to apply depending on the selection state of the edge.
   *
   * @param {*} edge The edge
   * @param {*} styleObject The styles that will be applied
   */
  applyFeedback(edge, styleObject) {
    if (edge.selected) {
      styleObject['stroke'] = 'var(--blue-lagoon)';
    }
    if (edge.hoverFeedback) {
      styleObject['stroke'] = 'var(--blue-lagoon)';
      styleObject['stroke-width'] = 2;
    }
  }

  /**
   * Renders additionals element for the given Edge. This method is overriden in order to let
   * us add decorators to the beggining or the end of the edge (to create an arrow for example).
   * @param edge The edge
   * @param segments The segments
   * @param context The context
   */
  renderAdditionals(edge, segments, context) {
    const { style } = edge;
    const styleObject = {
      stroke: style.color,
      'stroke-width': style.size,
      fill: style.color,
      sourceArrowStyle: style.sourceArrow,
      targetArrowStyle: style.targetArrow,
    };

    this.applyFeedback(edge, styleObject);

    const source = this.renderSource(styleObject, segments);
    const target = this.renderTarget(styleObject, segments);
    return [...source, ...target];
  }

  renderSource(styleObject, segments) {
    const p1 = segments[1];
    const p2 = segments[0];

    return this.renderArrow(p1, p2, styleObject.sourceArrowStyle, styleObject);
  }

  renderTarget(styleObject, segments) {
    const p1 = segments[segments.length - 2];
    const p2 = segments[segments.length - 1];
    return this.renderArrow(p1, p2, styleObject.targetArrowStyle, styleObject);
  }

  renderArrow(p1, p2, arrowStyle, styleObject) {
    const strokeWidth = styleObject !== undefined ? styleObject['stroke-width'] : 1;

    // Path definitions scaled with the stroke-width style attribute
    const basicArrowPath = `m ${-5 - strokeWidth}  ${-3.5 - strokeWidth} L 0 0 L ${-5 - strokeWidth} ${
      3.5 + strokeWidth
    }`;
    const basicHalfArrowPath = `m ${-8 - strokeWidth}  ${-3.5 - strokeWidth} L 0 0`;
    const basicDiamondPath = `m 0 0 L ${5 + strokeWidth} ${-3.5 - strokeWidth}  L ${10 + strokeWidth * 2} 0 L ${
      5 + strokeWidth
    } ${3.5 + strokeWidth} z`;
    const basicRoundPath = `m 0 0 a 4,4 0 1,1 8.0,0 a 4,4 0 1,1 -8.0,0`;
    const offsetArrowPath = `m ${(5 + strokeWidth) * 2} 0 L ${(5 + strokeWidth) * 2 + (5 + strokeWidth)} ${
      -3.5 - strokeWidth
    } M ${(5 + strokeWidth) * 2} 0 L ${(5 + strokeWidth) * 2 + (5 + strokeWidth)} ${3.5 + strokeWidth}`;

    let styleObjectCopy = { ...styleObject };
    let arrow;
    if (arrowStyle === 'OutputArrow') {
      arrow = this.buildOutputArrow(basicArrowPath, p1, p2, styleObjectCopy, strokeWidth);
    } else if (arrowStyle === 'InputHalfArrow') {
      arrow = this.buildInputHalfArrow(basicHalfArrowPath, p1, p2, styleObjectCopy);
    } else if (arrowStyle === 'InputArrow') {
      arrow = this.buildInputArrow(basicArrowPath, p1, p2, styleObjectCopy);
    } else if (arrowStyle === 'OutputClosedArrow') {
      arrow = this.buildOutputClosedArrow(basicArrowPath + 'z', p1, p2, styleObjectCopy, strokeWidth);
    } else if (arrowStyle === 'InputClosedArrow') {
      arrow = this.buildInputClosedArrow(basicArrowPath + 'z', p1, p2, styleObjectCopy);
    } else if (arrowStyle === 'OutputFillClosedArrow') {
      arrow = this.buildOutputFillClosedArrow(basicArrowPath + 'z', p1, p2, styleObjectCopy, strokeWidth);
    } else if (arrowStyle === 'InputFillClosedArrow') {
      arrow = this.buildInputFillClosedArrow(basicArrowPath + 'z', p1, p2, styleObjectCopy);
    } else if (arrowStyle === 'Round') {
      arrow = this.buildRoundArrow(basicRoundPath, p1, p2, styleObjectCopy);
    } else if (arrowStyle === 'Diamond') {
      arrow = this.buildDiamondArrow(basicDiamondPath, p1, p2, styleObjectCopy);
    } else if (arrowStyle === 'FillDiamond') {
      arrow = this.buildFillDiamondArrow(basicDiamondPath, p1, p2, styleObjectCopy);
    } else if (arrowStyle === 'InputArrowWithDiamond') {
      arrow = this.buildInputArrowWithDiamond(basicDiamondPath + offsetArrowPath, p1, p2, styleObjectCopy, strokeWidth);
    } else if (arrowStyle === 'InputArrowWithFillDiamond') {
      arrow = this.buildInputArrowWithFillDiamond(
        basicDiamondPath + offsetArrowPath,
        p1,
        p2,
        styleObjectCopy,
        strokeWidth
      );
    } else if (arrowStyle === 'Circle') {
      arrow = this.buildCircleArrow(p1, p2, styleObjectCopy, strokeWidth);
    } else if (arrowStyle === 'FillCircle') {
      arrow = this.buildFillCircleArrow(p1, p2, styleObjectCopy, strokeWidth);
    }

    return [arrow];
  }

  buildOutputArrow(path, p1, p2, styleObject, strokeWidth) {
    styleObject.fill = '#ffffff';
    const offsetX = p2.x + strokeWidth;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildInputHalfArrow(path, p1, p2, styleObject) {
    styleObject.fill = 'transparent';
    const offsetX = p2.x;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p1, p2));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildInputArrow(path, p1, p2, styleObject) {
    styleObject.fill = 'transparent';
    const offsetX = p2.x;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p1, p2));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildOutputClosedArrow(path, p1, p2, styleObject, strokeWidth) {
    styleObject.fill = '#ffffff';
    const offsetX = p2.x + 5.5 + strokeWidth;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildInputClosedArrow(path, p1, p2, styleObject) {
    styleObject.fill = '#ffffff';
    const offsetX = p2.x;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p1, p2));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildOutputFillClosedArrow(path, p1, p2, styleObject, strokeWidth) {
    const offsetX = p2.x + 5.5 + strokeWidth;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildInputFillClosedArrow(path, p1, p2, styleObject) {
    const offsetX = p2.x;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p1, p2));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildDiamondArrow(path, p1, p2, styleObject) {
    styleObject.fill = '#ffffff';
    const offsetX = p2.x;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }
  buildRoundArrow(path, p1, p2, styleObject) {
    styleObject.fill = '#000000';
    const offsetX = p2.x;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildFillDiamondArrow(path, p1, p2, styleObject) {
    const offsetX = p2.x;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildInputArrowWithDiamond(path, p1, p2, styleObject, strokeWidth) {
    styleObject.fill = '#ffffff';
    const offsetX = p2.x + strokeWidth;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildInputArrowWithFillDiamond(path, p1, p2, styleObject, strokeWidth) {
    const offsetX = p2.x + strokeWidth;
    const offsetY = p2.y;
    const rotationAngle = toDegrees(angle(p2, p1));
    const rotationX = p2.x;
    const rotationY = p2.y;
    return this.buildArrowPath(path, offsetX, offsetY, styleObject, rotationAngle, rotationX, rotationY);
  }

  buildCircleArrow(p1, p2, styleObject, strokeWidth) {
    styleObject.fill = '#ffffff';
    const rotation = `rotate(${toDegrees(angle(p2, p1))} ${p2.x} ${p2.y})`;
    const transform = `${rotation} translate(${p2.x + 5} ${p2.y})`;
    return <circle transform={transform} r={4 + strokeWidth} style={styleObject} />;
  }

  buildFillCircleArrow(p1, p2, styleObject, strokeWidth) {
    const rotation = `rotate(${toDegrees(angle(p2, p1))} ${p2.x} ${p2.y})`;
    const transform = `${rotation} translate(${p2.x + 5} ${p2.y})`;
    return <circle transform={transform} r={4 + strokeWidth} style={styleObject} />;
  }

  buildArrowPath(path, translateX, translateY, styleObject, rotationAngle, rotationX, rotationY) {
    let rotation = '';
    if (rotationAngle !== undefined) {
      if (rotationX !== undefined && rotationY !== undefined) {
        rotation = `rotate(${rotationAngle} ${rotationX} ${rotationY})`;
      } else {
        rotation = `rotate(${rotationAngle})`;
      }

      const transform = `${rotation} translate(${translateX} ${translateY})`;
      return <path class-edge={true} class-arrow={true} d={path} transform={transform} style={styleObject} />;
    }
  }
}

// range (-PI, PI]
function angle(a, b) {
  return Math.atan2(b.y - a.y, b.x - a.x);
}
