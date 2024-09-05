/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { FooterProps } from './Footer.types';

const useFooterStyles = makeStyles()((theme) => ({
  footer: {
    display: 'flex',
    justifyContent: 'center',
    gap: theme.spacing(0.5),
    margin: theme.spacing(2),
    '& > *': {
      marginLeft: theme.spacing(0.5),
      marginRight: theme.spacing(0.5),
    },
  },
}));

export const SiriusWebFooter = ({}: FooterProps) => {
  const { classes } = useFooterStyles();
  return (
    <footer className={classes.footer}>
      <Typography variant="caption">&copy; {new Date().getFullYear()} Obeo. Powered by</Typography>
      <Link variant="caption" href="https://www.eclipse.dev/sirius" rel="noopener noreferrer" target="_blank">
        Sirius Web
      </Link>
    </footer>
  );
};
