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

import IconButton from '@material-ui/core/IconButton';
import { styled } from '@material-ui/core/styles';
import { CSSProperties } from '@material-ui/core/styles/withStyles';

export const cardLabelFontStyle: CSSProperties = {
  fontSize: '10px',
  textAlign: 'right',
};

export const cardDetailFontStyle: CSSProperties = {
  fontSize: '12px',
  minHeight: '20px',
};

export const DeckDraggableCardWrapper = styled('article')(({ theme }) => ({
  borderRadius: ' 3px',
  borderBottom: `1px solid ${theme.palette.divider}`,
  backgroundColor: 'white',
  position: 'relative',
  padding: '10px',
  cursor: 'pointer',
  maxWidth: '250px',
  marginBottom: '7px',
  minWidth: '230px',
}));

export const DeckCardHeader = styled('article')(({ theme }) => ({
  marginBottom: '10px',
  display: 'flex',
  flexDirection: 'row',
  alignItems: 'flex-start',
  borderBottom: `1px solid ${theme.palette.divider}`,
  paddingBottom: '6px',
  color: theme.palette.text.primary,
}));

export const DeckCardRightContent = styled('span')({
  width: ' 38%',
  paddingRight: '10px',
});

export const CardDeleteIconButton = styled(IconButton)({
  position: 'absolute',
  top: '2px',
  right: '2px',
  padding: '0',
  ' & .MuiSvgIcon-fontSizeSmall': {
    fontSize: '12px',
  },
});

export const Detail = styled('div')(({ theme }) => ({
  color: theme.palette.text.primary,
  whiteSpace: 'pre-wrap',
}));

export const Footer = styled('div')(({ theme }) => ({
  borderTop: `1px solid ${theme.palette.divider}`,
  paddingTop: '6px',
  textAlign: 'right',
  display: 'flex',
  justifyContent: 'flex-end',
  flexDirection: 'row',
  flexWrap: 'wrap',
}));

export const TagSpan = styled('span')({
  padding: ' 2px 3px',
  borderRadius: '3px',
  margin: '2px 5px',
  fontSize: '70%',
});
