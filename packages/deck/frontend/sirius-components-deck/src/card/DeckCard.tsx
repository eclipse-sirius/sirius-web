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

import { Theme, useTheme } from '@mui/material/styles';
import { CSSProperties, useRef } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DeckInput } from '../common/DeckInput';
import { DeckTitle, titleFontStyle } from '../styled/DeckStyledComponents';
import { DeckCardProps, UpdateCardProps } from './DeckCard.types';
import { DeckDeleteButton } from './DeckDeleteButton';
import { DeckTag } from './DeckTag';

const useStyles = makeStyles()((theme: Theme) => ({
  detail: {
    color: theme.palette.text.primary,
    whiteSpace: 'pre-wrap',
  },
  footer: {
    borderTop: `1px solid ${theme.palette.divider}`,
    paddingTop: '6px',
    textAlign: 'right',
    display: 'flex',
    justifyContent: 'flex-end',
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  rightContent: {
    width: ' 38%',
    paddingRight: '10px',
  },
  header: {
    marginBottom: '10px',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'flex-start',
    borderBottom: `1px solid ${theme.palette.divider}`,
    paddingBottom: '6px',
    color: theme.palette.text.primary,
  },
  cardWrapper: {
    borderRadius: ' 3px',
    borderBottom: `1px solid ${theme.palette.divider}`,
    backgroundColor: 'white',
    position: 'relative',
    padding: '10px',
    cursor: 'pointer',
    maxWidth: '250px',
    marginBottom: '7px',
    minWidth: '230px',
  },
}));

const cardLabelFontStyle: CSSProperties = {
  fontSize: '10px',
  textAlign: 'right',
};

const cardDetailFontStyle: CSSProperties = {
  fontSize: '12px',
  minHeight: '20px',
};

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
  const { classes } = useStyles();

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
    <article
      tabIndex={0}
      data-id={id}
      onClick={onClick}
      style={cardStyle}
      className={`${className} ${classes.cardWrapper}`}
      onKeyDown={handleKeyDown}
      data-testid={`card-${title}`}
      onDragStart={(e) => e.preventDefault()}>
      <article className={classes.header}>
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
        <span className={classes.rightContent} style={cardLabelFontStyle}>
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
        </span>
        {editable && <DeckDeleteButton onClick={handleOnDelete} />}
      </article>
      <div className={classes.detail} style={cardDetailFontStyle}>
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
      </div>
      {tags && tags.length > 0 && (
        <div className={classes.footer}>
          {tags.map((tag) => (
            <DeckTag key={tag.title} {...tag} tagStyle={tagStyle} />
          ))}
        </div>
      )}
    </article>
  );
};
