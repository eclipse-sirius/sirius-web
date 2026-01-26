/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from 'tss-react/mui';
import { ToolProps } from './Tool.types';

const useToolStyle = makeStyles()((theme) => ({
  tool: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    cursor: 'pointer',
  },
}));

export const Tool = ({ tool, onClick }: ToolProps) => {
  const { id, label, iconURL } = tool;
  const { classes } = useToolStyle();
  let image: JSX.Element | null = null;
  if (iconURL.length > 0) {
    image = <IconOverlay iconURLs={iconURL} alt={label} title={label} />;
  }
  const onToolClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
    onClick(tool);
  };

  return (
    <div key={id} className={classes.tool} onClick={onToolClick} data-testid={`${tool.label} - Tool`}>
      {image}
    </div>
  );
};
