/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { setAttr, setNamespace, SLabelView, svg } from 'sprotty';
import { getSubType } from 'sprotty-protocol';
const preventRemovalOfUnusedImportByPrettier = svg !== null;

/**
 * The view used to display inside labels.
 *
 * @arichard
 */
export class InsideLabelView extends SLabelView {
  // @ts-ignore
  render(label) {
    const { color, bold, underline, strikeThrough, italic, fontSize, iconURL, opacity } = label.style;

    const width: number = label.size.width;
    const height: number = label.size.height;

    // The font-family is hardcoded to match with the backend compute bounds algo.
    const textStyle = {
      width: width + 'px',
      height: height + 'px',
      color: color,
      opacity: opacity,
      'font-size': fontSize + 'px',
      'font-family': 'Arial, Helvetica, sans-serif',
      'font-weight': 'normal',
      'font-style': 'normal',
      'overflow-wrap': 'anywhere',
      'white-space': 'break-spaces',
      'letter-spacing': 'normal',
      'line-height': '1',
      display: 'flex',
      'flex-wrap': 'no-wrap',
    };
    if (bold) {
      textStyle['font-weight'] = 'bold';
    }
    if (italic) {
      textStyle['font-style'] = 'italic';
    }
    if (underline) {
      textStyle['text-decoration'] = 'underline';
    }
    if (strikeThrough) {
      if (textStyle['text-decoration'] === 'none') {
        textStyle['text-decoration'] = 'line-through';
      } else {
        textStyle['text-decoration'] += ' line-through';
      }
    }
    if (label.type.endsWith('-h_left')) {
      textStyle['text-align'] = 'left';
      textStyle['justify-content'] = 'flex-start';
    } else if (label.type.endsWith('-h_right')) {
      textStyle['text-align'] = 'right';
      textStyle['justify-content'] = 'flex-end';
    } else {
      textStyle['text-align'] = 'center';
      textStyle['justify-content'] = 'center';
    }
    if (label.type.includes('-v_top')) {
      textStyle['align-items'] = 'flex-start';
    } else if (label.type.includes('-v_bottom')) {
      textStyle['align-items'] = 'flex-end';
    } else {
      textStyle['align-items'] = 'center';
    }

    const foreignObjectContents = (
      <div style={textStyle}>
        {iconURL ? <img height="16" width="16" alt="" style={{ marginRight: '4px' }} src={iconURL} /> : ''}
        {label.text}
      </div>
    );
    const vnode = (
      <foreignObject
        requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"
        height={height}
        width={width}
        class-sprotty-label={true}
        attrs-data-testid={`Label - ${label.text}`}
        style={{ pointerEvents: 'none' }}>
        {foreignObjectContents}
      </foreignObject>
    );
    const subType: string = getSubType(label);
    if (subType) {
      // @ts-ignore
      setAttr(vnode, 'class', subType);
      // @ts-ignore
      setNamespace(foreignObjectContents, 'http://www.w3.org/1999/xhtml');
    }
    return vnode;
  }
}
