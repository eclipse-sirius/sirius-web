/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { getSubType, setAttr, SLabelView } from 'sprotty';

/**
 * The view used to display labels.
 *
 * @sbegaudeau
 */
export class LabelView extends SLabelView {
  // @ts-ignore
  render(label) {
    const { color, bold, underline, strikeThrough, italic, fontSize, iconURL } = label.style;
    // The font-family is hardcoded to match with the backend compute bounds algo.
    const styleObject = {
      fill: color,
      'font-size': fontSize + 'px',
      'font-family': 'Arial, Helvetica, sans-serif',
      'font-weight': 'normal',
      'font-style': 'normal',
      'text-decoration': 'none',
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
    const iconVerticalOffset = -12;
    const vnode = (
      <g attrs-data-testid={`Label - ${label.text}`}>
        {iconURL ? <image href={iconURL} y={iconVerticalOffset} x="-20" /> : ''}
        <text class-sprotty-label={true} style={styleObject}>
          {label.text}
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
