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

import { useTheme } from '@material-ui/core/styles';
import { useRef } from 'react';
import { Card } from '../Deck.types';
import { DeckCardProps } from './DeckCard.types';
import { DeckCardInput } from './DeckCardInput';
import {
  DeckCardHeader,
  DeckCardRightContent,
  DeckCardTitle,
  DeckDraggableCardWrapper,
  Detail,
  Footer,
  cardDetailFontStyle,
  cardLabelFontStyle,
  cardTitleFontStyle,
} from './DeckCardStyledComponents';
import { DeckDeleteButton } from './DeckDeleteButton';
import { DeckTag } from './DeckTag';

/**
 * A specific Card Component to handle selection and direct edit.
 * Copied and adapted from react-trello Card component.
 */
export const DeckCard = ({
  style,
  tagStyle,
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
  t,
}: DeckCardProps) => {
  const updateCard = (card: Card) => {
    onChange(card);
  };

  const handleOnDelete = (e) => {
    onDelete();
    e.stopPropagation();
  };

  const theme = useTheme();

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
      data-testid={`card-${title}`}>
      <DeckCardHeader>
        <DeckCardTitle draggable={cardDraggable} style={cardTitleFontStyle}>
          {editable ? (
            <DeckCardInput
              ref={titleInputRef}
              value={title}
              placeholder={t('placeholder.title')}
              onSave={(value) => updateCard({ title: value, label, description, id })}
              style={cardTitleFontStyle}
              data-testid={'card-input-title'}
            />
          ) : (
            title
          )}
        </DeckCardTitle>
        <DeckCardRightContent style={cardLabelFontStyle}>
          {editable ? (
            <DeckCardInput
              value={label}
              placeholder={t('placeholder.label')}
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
          <DeckCardInput
            value={description}
            placeholder={t('placeholder.description')}
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
