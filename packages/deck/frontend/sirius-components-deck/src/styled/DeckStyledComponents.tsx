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

import { styled } from '@material-ui/core/styles';
import { CSSProperties } from '@material-ui/core/styles/withStyles';
import { DeckTitleProps } from './DeckStyledComponents.types';

export const titleFontStyle: CSSProperties = {
  fontWeight: 'bold',
  fontSize: '14px',
  lineHeight: '18px',
};

export const Title = styled('span')(({ theme }) => ({
  width: '70%',
  color: theme.palette.text.primary,
}));

export const DeckTitle = styled(Title)({
  cursor: (props: DeckTitleProps) => (props.draggable ? 'grab' : `auto`),
});

export const RightContent = styled('span')({
  width: '38%',
  textAlign: 'right',
  paddingRight: '10px',
  fontSize: '13px',
});
