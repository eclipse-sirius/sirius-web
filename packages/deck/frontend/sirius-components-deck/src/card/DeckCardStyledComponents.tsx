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
import { CSSProperties } from '@material-ui/core/styles/withStyles';
import styled from '@material-ui/styles/styled';

export const cardTitleFontStyle: CSSProperties = {
  fontWeight: 'bold',
  fontSize: '14px',
  lineHeight: '18px',
};

export const cardLabelFontStyle: CSSProperties = {
  fontSize: '10px',
  textAlign: 'right',
};

export const cardDetailFontStyle: CSSProperties = {
  fontSize: '12px',
};

export const DeckDraggableCardWrapper = styled('article')({
  borderRadius: ' 3px',
  borderBottom: '1px solid rgb(204, 204, 204)',
  backgroundColor: 'rgb(255, 255, 255)',
  position: 'relative',
  padding: '10px',
  cursor: 'pointer',
  maxWidth: '250px',
  marginBottom: '7px',
  minWidth: '230px',
});

export const DeckCardHeader = styled('article')({
  marginBottom: '10px',
  display: 'flex',
  flexDirection: 'row',
  alignItems: 'flex-start',
  borderBottom: ' 1px solid #eee',
  paddingBottom: ' 6px',
  color: ' #000',
});

export const DeckCardTitle = styled('span')({
  cursor: (props) => (props.draggable ? 'grab' : `auto`),
  width: '70%',
  ...cardTitleFontStyle,
});

export const DeckCardRightContent = styled('span')({
  width: ' 38%',
  paddingRight: '10px',
  ...cardLabelFontStyle,
});

export const DeleteWrapper = styled('span')({
  textAlign: 'center',
  position: 'absolute',
  top: '-1px',
  right: '2px',
  cursor: 'pointer',
});

export const DelButton = styled('button')({
  transition: 'all 0.5s ease',
  display: 'inline-block',
  border: 'none',
  fontSize: '8px',
  height: '15px',
  lineHeight: '1px',
  margin: '0 0 8px',
  padding: '0',
  textAlign: 'center',
  width: '15px',
  background: 'inherit',
  cursor: 'pointer',
  opacity: '1',
});

export const Detail = styled('div')({
  color: '#4d4d4d',
  whiteSpace: 'pre-wrap',
  ...cardDetailFontStyle,
});

export const Footer = styled('div')({
  borderTop: '1px solid #eee',
  paddingTop: '6px',
  textAlign: 'right',
  display: 'flex',
  justifyContent: 'flex-end',
  flexDirection: 'row',
  flexWrap: 'wrap',
});

export const TagSpan = styled('span')({
  padding: ' 2px 3px',
  borderRadius: '3px',
  margin: '2px 5px',
  fontSize: '70%',
});
