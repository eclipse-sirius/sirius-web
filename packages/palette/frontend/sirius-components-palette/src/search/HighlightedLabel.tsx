/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import ListItemText from '@mui/material/ListItemText';
import { Theme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { HighlightedLabelProps } from './HighlightedLabel.types';

const useLabelStyles = makeStyles()((theme: Theme) => ({
  highlight: {
    backgroundColor: theme.palette.navigation.leftBackground,
  },
  itemText: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
}));

export const HighlightedLabel = ({ label, textIndicesToHighlight }: HighlightedLabelProps) => {
  const { classes } = useLabelStyles();
  const itemLabel: React.JSX.Element = (
    <>
      {label.split('').map((value, index) => {
        const shouldHighlight = textIndicesToHighlight.includes(index);
        return (
          <span
            key={value + index}
            data-testid={`${label}-${value}-${index}`}
            className={shouldHighlight ? classes.highlight : ''}>
            {value}
          </span>
        );
      })}
    </>
  );
  return (
    <ListItemText disableTypography>
      <Typography className={classes.itemText}>{itemLabel}</Typography>
    </ListItemText>
  );
};
