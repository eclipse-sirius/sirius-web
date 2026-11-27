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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { TreeItemDecoratorsProps } from './TreeItemDecorators.types';

const useTreeItemDecoratorsStyle = makeStyles()((theme) => ({
  decorator: {
    display: 'flex',
    alignItems: 'center',
    gap: theme.spacing(0.5),
    fontSize: theme.typography.body2.fontSize,
    whiteSpace: 'nowrap',
  },
  icon: {
    objectFit: 'contain',
  },
}));

export const TreeItemDecorators = ({ decorators }: TreeItemDecoratorsProps): React.JSX.Element => {
  const { classes } = useTreeItemDecoratorsStyle();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  return (
    <>
      {decorators.map(({ iconURL, label }, index): React.JSX.Element => {
        const iconSource = iconURL.startsWith('/') && !iconURL.startsWith('//') ? httpOrigin + iconURL : iconURL;
        return (
          <span key={index} className={classes.decorator}>
            <img className={classes.icon} src={iconSource} alt="" width={16} height={16} />
            {label}
          </span>
        );
      })}
    </>
  );
};
