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

import KeyboardCommandKeyIcon from '@mui/icons-material/KeyboardCommandKey';
import { makeStyles } from 'tss-react/mui';
import { KeyBindingProps } from './KeyBinding.types';

const useKeyBindingStyle = makeStyles()((theme) => ({
  keyBinding: {
    flex: '0 0 auto',
    marginLeft: 'auto',
    minWidth: 'fit-content',
    overflow: 'hidden',
    display: 'flex',
    alignItems: 'center',
    color: theme.palette.text.disabled,
  },
}));

export const KeyBinding = ({ keyBinding, 'data-testid': dataTestid }: KeyBindingProps) => {
  const { classes } = useKeyBindingStyle();
  return (
    <div className={classes.keyBinding} data-testid={dataTestid}>
      {keyBinding.isCtrl ? 'Ctrl+' : ''}
      {keyBinding.isMeta ? <KeyboardCommandKeyIcon /> : null}
      {keyBinding.isAlt ? 'Alt+' : ''}
      {keyBinding.key}
    </div>
  );
};
