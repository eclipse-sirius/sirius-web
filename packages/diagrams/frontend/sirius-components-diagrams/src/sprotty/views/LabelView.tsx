/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
  const { text, size, iconSize } = attrs;

  //Look for the index of the longest string
  const lines = text.split('\n');
  const getLongestText = (arr) =>
    arr.reduce((savedText, text) => (text.length > savedText.length ? text : savedText), '');
  const indexLongestText = lines.indexOf(getLongestText(lines));

  const ratioLinesHeight = size.height / lines.length;

  //The length of the text minus the icon size, minus the icon size again if it's not the first line
  let textLength = size.width - iconSize;
  console.log(textLength);
  if (indexLongestText > 0) textLength -= iconSize;
  console.log(textLength);

  // avoid tspan to be ignored if there is only a line return
  return lines.map((line: string, index: number) => {
    return (
      <tspan
        x={index === 0 ? iconSize : 0}
        dy={ratioLinesHeight}
        lengthAdjust="spacing"
        {...(indexLongestText === index ? { textLength: textLength } : {})}>
        {line.length == 0 ? ' ' : line}
      </tspan>
    );
  });
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
    const size = label.size;
    // The font-family is hardcoded to match with the backend compute bounds algo.
    const styleObject = {
      fill: color,
      'font-size': fontSize + 'px',
      'font-family': 'Arial, Helvetica, sans-serif',
      ...(bold ? { 'font-weight': 'bold' } : { 'font-weight': 'normal' }),
      ...(italic ? { 'font-style': 'italic' } : { 'font-style': 'normal' }),
      'font-style': 'normal',
      'text-decoration': 'none',
      'text-anchor': 'start',
      'letter-spacing': 'normal',
      opacity: opacity,
    };

    if (underline) styleObject['text-decoration'] = 'underline';

    if (strikeThrough)
      if (styleObject['text-decoration'] === 'none') styleObject['text-decoration'] = 'line-through';
      else styleObject['text-decoration'] += ' line-through';

    const styleIcon = {
      opacity: styleObject.opacity,
    };

    const text = label.text;
    const iconSize = iconURL ? 20 : 0;

    const vnode = (
      <g attrs-data-testid={`Label - ${label.text}`}>
        {iconURL ? <image href={iconURL} style={styleIcon} /> : ''}
        <text class-sprotty-label={true} style={styleObject}>
          <Text text={text} fontSize={fontSize} size={size} iconSize={iconSize} />
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
