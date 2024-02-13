/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import SvgIcon, { SvgIconProps } from '@material-ui/core/SvgIcon';

export const HelperLinesIconOff = (props: SvgIconProps) => {
  return (
    <SvgIcon
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <path d="M22,11V9h-7V2h-2v7h-2V2H9v7H2v2h7v2H2v2h7v7h2v-7h2v7h2v-7h7v-2h-7v-2H22z M13,13h-2v-2h2V13z" />
      <line
        fill="none"
        id="svg_10"
        stroke="#757575"
        stroke-dasharray="null"
        stroke-linecap="null"
        stroke-linejoin="null"
        stroke-opacity="null"
        stroke-width="2"
        x1="3"
        x2="20"
        y1="3.5"
        y2="21"
      />
      <line
        fill="none"
        id="svg_12"
        stroke="#ffffff"
        stroke-dasharray="null"
        stroke-linecap="null"
        stroke-linejoin="null"
        stroke-width="1.75"
        x1="4.12"
        x2="21.06"
        y1="2.42"
        y2="19.78"
      />
    </SvgIcon>
  );
};
