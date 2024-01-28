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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { ToolProps } from './Tool.types';

const useToolStyle = makeStyles(() => ({
  tool: {
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: '20px 1fr',
    alignItems: 'center',
    cursor: 'pointer',
  },
}));

export const Tool = ({ tool, onClick, thumbnail }: ToolProps) => {
  const { id, label, iconURL } = tool;
  const classes = useToolStyle();
  let image: JSX.Element | null = null;
  if (iconURL.length > 0) {
    image = <IconOverlay iconURL={iconURL} alt={label} title={label} />;
  }
  let labelContent: JSX.Element | null = null;
  if (!thumbnail) {
    labelContent = <Typography>{label}</Typography>;
  }

  const onToolClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
    onClick(tool);
  };

  return (
    <div key={id} className={classes.tool} onClick={onToolClick} data-testid={`${tool.label} - Tool`}>
      {image}
      {labelContent}
    </div>
  );
};
