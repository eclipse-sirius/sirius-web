/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import Tooltip from '@material-ui/core/Tooltip';
import { memo, useContext } from 'react';
import { ServerContext } from '../contexts/ServerContext';
import { ServerContextValue } from '../contexts/ServerContext.types';
import { IconOverlayProps } from './IconOverlay.types';

export const IconOverlay = memo(
  ({ iconURL, alt, title, customIconWidth, customIconHeight, customIconStyle }: IconOverlayProps) => {
    const iconWidth: number = customIconWidth || 16;
    const iconHeight: number = customIconHeight || 16;
    const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

    return (
      <>
        {iconURL?.length > 0 && (
          <div style={{ position: 'relative', width: iconWidth, height: iconHeight, ...customIconStyle }}>
            {iconURL.map((url: string, index) => (
              <Tooltip title={title || ''} key={'tooltip_' + index}>
                <img
                  height={iconHeight}
                  width={iconWidth}
                  key={index}
                  alt={alt}
                  src={httpOrigin + url}
                  style={{ zIndex: index, position: 'absolute', top: 0, left: 0 }}
                />
              </Tooltip>
            ))}
          </div>
        )}
      </>
    );
  }
);
