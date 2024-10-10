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

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import Button from '@mui/material/Button';
import { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { OmniboxButtonProps } from './OmniboxButton.types';
import { OmniboxContext } from './OmniboxContext';
import { OmniboxContextValue } from './OmniboxContext.types';

const useOmniboxButtonStyles = makeStyles()((theme) => ({
  omniboxButton: {
    color: 'inherit',
    border: `1px solid ${theme.palette.background.paper}`,
  },
  placeholder: {
    verticalAlign: 'middle',
    lineHeight: '16px',
  },
  omniboxField: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    border: `1px solid ${theme.palette.background.paper}`,
    borderRadius: '3px',
    marginLeft: theme.spacing(4),
    fontSize: '0.75rem',
    fontWeight: 700,
    lineHeight: '20px',
    padding: '0px 4px',
    fontFamily: 'sans-serif',
    opacity: 0.7,
  },
}));

export const OmniboxButton = ({ size = 'small' }: OmniboxButtonProps) => {
  const { classes } = useOmniboxButtonStyles();

  const { openOmnibox } = useContext<OmniboxContextValue>(OmniboxContext);

  const handleClick = () => openOmnibox();

  var isApple = /(Mac|iPhone|iPod|iPad)/i.test(navigator.platform);
  return (
    <Button startIcon={<ChevronRightIcon />} onClick={handleClick} className={classes.omniboxButton} size={size}>
      <div className={classes.placeholder}>Run commands...</div>
      <div className={classes.omniboxField}>{isApple ? '⌘ ' : 'Ctrl '}+ K</div>
    </Button>
  );
};
