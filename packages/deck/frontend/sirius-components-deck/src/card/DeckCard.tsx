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
import { useRef } from 'react';
import { DeckInput } from '../common/DeckInput';
import {
  DeckCardHeader,
  DeckCardRightContent,
  DeckDraggableCardWrapper,
  Detail,
  Footer,
  cardDetailFontStyle,
  cardLabelFontStyle,
} from '../styled/DeckCardStyledComponents';
import { DeckTitle, titleFontStyle } from '../styled/DeckStyledComponents';
import { DeckCardProps, UpdateCardProps } from './DeckCard.types';
import { DeckDeleteButton } from './DeckDeleteButton';
import { DeckTag } from './DeckTag';

/**
 * A specific Card Component to handle selection and direct edit.
 * Copied and adapted from react-trello Card component.
 */
export const DeckCard = ({
  style,
  tagStyle,
  titleStyle = titleFontStyle,
  onClick,
  onDelete,
  onChange,
  className,
  id,
  title,
  label,
  description,
  tags,
  cardDraggable,
  editable,
  t: translate,
}: DeckCardProps) => {
  const updateCard = (updateCardValue: UpdateCardProps) => {
    onChange(updateCardValue);
  };

  const handleOnDelete = (e) => {
    onDelete();
    e.stopPropagation();
  };

  const theme: Theme = useTheme();

  const cardStyle: React.CSSProperties = {
    border: editable ? `2px solid ${theme.palette.selected}` : undefined,
    ...style,
  };

  const titleInputRef = useRef<HTMLInputElement | HTMLTextAreaElement | null>(null);

  const handleKeyDown = (e: React.KeyboardEvent<HTMLDivElement>) => {
    if (titleInputRef.current) {
      if (e.key === 'F2') {
        titleInputRef.current.select();
      }
    }
  };
  return (
    <DeckDraggableCardWrapper
      tabIndex={0}
      data-id={id}
      onClick={onClick}
      style={cardStyle}
      className={className}
      onKeyDown={handleKeyDown}
      data-testid={`card-${title}`}
      onDragStart={(e) => e.preventDefault()}>
      <DeckCardHeader>
        <DeckTitle draggable={cardDraggable} style={titleStyle} theme={theme} data-testid={`card-${title}-title`}>
          {editable ? (
            <DeckInput
              ref={titleInputRef}
              value={title}
              placeholder={translate('placeholder.title')}
              onSave={(value) => updateCard({ title: value, label, description, id })}
              style={titleStyle}
              data-testid={'card-input-title'}
            />
          ) : (
            title
          )}
        </DeckTitle>
        <DeckCardRightContent style={cardLabelFontStyle}>
          {editable ? (
            <DeckInput
              value={label}
              placeholder={translate('placeholder.label')}
              onSave={(value) => updateCard({ title, label: value, description, id })}
              style={cardLabelFontStyle}
              data-testid={'card-input-label'}
            />
          ) : (
            label
          )}
        </DeckCardRightContent>
        {editable && <DeckDeleteButton onClick={handleOnDelete} />}
      </DeckCardHeader>
      <Detail style={cardDetailFontStyle}>
        {editable ? (
          <DeckInput
            value={description}
            placeholder={translate('placeholder.description')}
            onSave={(value) => updateCard({ title, label, description: value, id })}
            style={cardDetailFontStyle}
            multiline
            data-testid={'card-input-details'}
          />
        ) : (
          description
        )}
      </Detail>
      {tags && tags.length > 0 && (
        <Footer>
          {tags.map((tag) => (
            <DeckTag key={tag.title} {...tag} tagStyle={tagStyle} />
          ))}
        </Footer>
      )}
    </DeckDraggableCardWrapper>
  );
};
