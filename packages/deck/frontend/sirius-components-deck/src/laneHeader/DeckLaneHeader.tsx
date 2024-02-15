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

import { Theme, makeStyles, useTheme } from '@material-ui/core/styles';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import { useEffect, useRef } from 'react';
import { DeckInput } from '../common/DeckInput';
import { LaneHeader } from '../styled/DeckLaneStyledComponents';
import { DeckTitle, RightContent, titleFontStyle } from '../styled/DeckStyledComponents';
import { DeckLaneHeaderProps } from './DeckLaneHeader.types';

import IconButton from '@material-ui/core/IconButton';
import { useLaneContextMenu } from './useLaneContextMenu';

const useLaneHeaderStyle = makeStyles((theme) => ({
  more: {
    hover: {
      backgroundColor: theme.palette.action.hover,
    },
    focus: {
      backgroundColor: theme.palette.action.selected,
    },
  },
}));

export const DeckLaneHeader = ({
  updateTitle,
  editLaneTitle,
  label,
  title,
  t: translate,
  laneDraggable,
  cards,
  id,
  titleStyle = titleFontStyle,
}: DeckLaneHeaderProps) => {
  const theme: Theme = useTheme();
  const titleInputRef = useRef<HTMLInputElement | HTMLTextAreaElement | null>(null);
  const headerRef = useRef<HTMLElement | null>(null);
  const { openContextMenu, contextMenu } = useLaneContextMenu(cards, id);

  useEffect(() => {
    if (headerRef.current) {
      if (editLaneTitle) {
        headerRef.current.focus();
      }
    }
  }, [editLaneTitle]);

  const handleKeyDown = (e: React.KeyboardEvent<HTMLDivElement>) => {
    if (titleInputRef.current) {
      if (e.key === 'F2') {
        titleInputRef.current.select();
      }
    }
  };

  const classes = useLaneHeaderStyle();
  return (
    <>
      <LaneHeader onKeyDown={handleKeyDown} tabIndex={0} ref={headerRef}>
        <DeckTitle draggable={laneDraggable} style={titleStyle} theme={theme} data-testid={`lane-${title}-title`}>
          {editLaneTitle ? (
            <DeckInput
              ref={titleInputRef}
              value={title}
              placeholder={translate('placeholder.title')}
              onSave={updateTitle}
              style={titleStyle}
              data-testid={'lane-input-title'}
            />
          ) : (
            title
          )}
        </DeckTitle>
        {label && <RightContent>{label}</RightContent>}
        <IconButton className={classes.more} size="small" onClick={openContextMenu} data-testid={`lane-${title}-more`}>
          <MoreVertIcon style={{ fontSize: 14 }} />
        </IconButton>
      </LaneHeader>
      {contextMenu}
    </>
  );
};
