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

import { PropertySectionFlexProps } from './PropertySection.types';

export type FlexDirection = 'column' | 'column-reverse' | 'row' | 'row-reverse';

const getFlexDirection = (flexDirectionString: string): FlexDirection => {
  let flexDirection;
  if (flexDirectionString === 'column') {
    flexDirection = 'column';
  } else if (flexDirectionString === 'column-reverse') {
    flexDirection = 'column-reverse';
  } else if (flexDirectionString === 'row') {
    flexDirection = 'row';
  } else if (flexDirectionString === 'row-reverse') {
    flexDirection = 'row-reverse';
  }

  return flexDirection;
};

export const getFlexProperties = (
  flexProps: PropertySectionFlexProps,
  defaultFlexDirection: FlexDirection = 'column'
) => {
  const { flexDirection, gap, labelFlex, valueFlex } = {
    ...flexProps,
  };
  const flexDirectionCSS = flexDirection ? getFlexDirection(flexDirection) : defaultFlexDirection;
  let alignItems = 'normal';
  if (flexDirectionCSS === 'row' || flexDirectionCSS === 'row-reverse') {
    alignItems = 'center';
  }

  return { flexDirectionCSS, alignItems, gap, labelFlex, valueFlex };
};
