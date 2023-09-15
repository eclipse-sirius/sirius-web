/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
/** @jsxRuntime classic */
import { setAttr, SLabelView, svg } from 'sprotty';
import { getSubType } from 'sprotty-protocol';

const preventRemovalOfUnusedImportByPrettier = svg !== null;

const Text = (props) => {
  const { attrs } = props;
  const { text, fontSize } = attrs;

  const lines = text.split('\n');
  if (lines.length == 1) {
    return text;
  } else {
    return lines.map((line, index) => {
      if (index === 0) {
        return <tspan x="0">{line}</tspan>;
      } else {
        if (line.length == 0) {
          // avoid tspan to be ignored if there is only a line return
          line = ' '; //$NON-NLS-1$
        }
        return (
          <tspan x="0" dy={fontSize}>
            {line}
          </tspan>
        );
      }
    });
  }
};

/**
 * The view used to display labels.
 *
 * @sbegaudeau
 */
export class LabelView extends SLabelView {
  // @ts-ignore
  render(label) {
    const { color, bold, underline, strikeThrough, italic, fontSize, iconURL, opacity } = label.style;
    // The font-family is hardcoded to match with the backend compute bounds algo.
    const styleObject = {
      fill: color,
      'font-size': fontSize + 'px',
      'font-family': 'Arial, Helvetica, sans-serif',
      'font-weight': 'normal',
      'font-style': 'normal',
      'text-decoration': 'none',
      'text-anchor': 'start',
      opacity: opacity,
    };
    if (bold) {
      styleObject['font-weight'] = 'bold';
    }
    if (italic) {
      styleObject['font-style'] = 'italic';
    }
    if (underline) {
      styleObject['text-decoration'] = 'underline';
    }
    if (strikeThrough) {
      if (styleObject['text-decoration'] === 'none') {
        styleObject['text-decoration'] = 'line-through';
      } else {
        styleObject['text-decoration'] += ' line-through';
      }
    }

    const styleIcon = {
      opacity: styleObject.opacity,
    };

    const iconVerticalOffset = -14;
    const text = label.text;

    const vnode = (
      <g attrs-data-testid={`Label - ${label.text}`}>
        {iconURL.length > 0
          ? iconURL.map((icon, index) => (
              <image href={icon} y={iconVerticalOffset} x="-20" style={styleIcon} key={index} />
            ))
          : ''}
        <text class-sprotty-label={true} style={styleObject}>
          <Text text={text} fontSize={fontSize} />
        </text>
      </g>
    );

    const subType = getSubType(label);
    if (subType) {
      // @ts-ignore
      setAttr(vnode, 'class', subType);
    }
    return vnode;
  }
}
