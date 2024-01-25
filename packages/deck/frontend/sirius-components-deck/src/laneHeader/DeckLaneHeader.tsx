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

import { Theme, useTheme } from '@material-ui/core/styles';
import { useEffect, useRef } from 'react';
import { DeckInput } from '../common/DeckInput';
import { LaneHeader } from '../styled/DeckLaneStyledComponents';
import { DeckTitle, RightContent, titleFontStyle } from '../styled/DeckStyledComponents';
import { DeckLaneHeaderProps } from './DeckLaneHeader.types';

export const DeckLaneHeader = ({
  updateTitle,
  editLaneTitle,
  label,
  title,
  t: translate,
  laneDraggable,
}: DeckLaneHeaderProps) => {
  const theme: Theme = useTheme();
  const titleInputRef = useRef<HTMLInputElement | HTMLTextAreaElement | null>(null);

  const headerRef = useRef<HTMLElement | null>(null);

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
  return (
    <LaneHeader onKeyDown={handleKeyDown} tabIndex={0} ref={headerRef}>
      <DeckTitle draggable={laneDraggable} style={titleFontStyle} theme={theme}>
        {editLaneTitle ? (
          <DeckInput
            ref={titleInputRef}
            value={title}
            placeholder={translate('placeholder.title')}
            onSave={updateTitle}
            style={titleFontStyle}
            data-testid={'lane-input-title'}
          />
        ) : (
          title
        )}
      </DeckTitle>
      {label && <RightContent>{label}</RightContent>}
    </LaneHeader>
  );
};
