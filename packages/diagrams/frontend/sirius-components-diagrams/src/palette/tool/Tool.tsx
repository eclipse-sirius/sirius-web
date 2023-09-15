/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useContext } from 'react';
import { GenericTool } from './GenericTool';
import { ToolProps } from './Tool.types';

const useToolStyles = makeStyles(() => ({
  tool: {
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: '20px 1fr',
    alignItems: 'center',
    cursor: 'pointer',
  },
  disabled: {
    cursor: 'default',
  },
  selected: {
    fontWeight: 'bold',
  },
  iconContainer: {
    position: 'relative',
    width: '16px',
    height: '16px',
  },
  icon: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
}));

export const Tool = ({ tool, selected, onClick, disabled, thumbnail }: ToolProps) => {
  const { id, label, iconURL } = tool;
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const classes = useToolStyles();

  let className = classes.tool;
  if (disabled) {
    className = `${className} ${classes.disabled}`;
  }

  let image = <GenericTool title={label} style={{ fill: 'var(--daintree)' }} />;
  if (iconURL.length > 0) {
    image = (
      <div className={classes.iconContainer}>
        {iconURL
          .filter((url) => url != '/api/images')
          .map((url, index) => {
            return (
              <img
                height="16"
                width="16"
                key={index}
                alt={label}
                title={label}
                src={url.startsWith('data:') ? url : httpOrigin + url}
                className={classes.icon}
                style={{ zIndex: index }}></img>
            );
          })}
      </div>
    );
  }
  let labelContent;
  if (!thumbnail) {
    labelContent = <Typography className={selected ? classes.selected : ''}>{label}</Typography>;
  }

  const onToolClick = () => {
    if (!disabled) {
      onClick(tool);
    }
  };
  const onKeyPress = (event) => {
    const { key } = event;
    // Space or enter will trigger the tool
    if (key === 'Enter' || key === ' ') {
      event.preventDefault();
      onClick(tool);
    }
  };
  return (
    <div
      className={className}
      key={id}
      onClick={onToolClick}
      onKeyPress={onKeyPress}
      tabIndex={0}
      data-testid={`${label} - Tool`}>
      {image}
      {labelContent}
    </div>
  );
};
