/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import {
  RepresentationComponentProps,
  RepresentationContext,
  RepresentationContextValue,
} from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import DeleteIcon from '@material-ui/icons/Delete';
import { useContext } from 'react';
import { RepresentationFrameProps } from './RepresentationFrame.types';

const useFrameStyles = makeStyles((theme) => ({
  representationFrame: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    overflow: 'auto',
  },
  frameHeader: {
    backgroundColor: theme.palette.grey[300],
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
}));

export const RepresentationFrame = ({
  editingContextId,
  representation,
  readOnly,
  onDelete,
}: RepresentationFrameProps) => {
  const { registry } = useContext<RepresentationContextValue>(RepresentationContext);

  const RepresentationComponent = registry.getComponent(representation);
  const props: RepresentationComponentProps = {
    editingContextId,
    readOnly,
    representationId: representation.id,
  };
  if (RepresentationComponent) {
    const classes = useFrameStyles();
    return (
      <div data-testid={`representation-frame-${representation.id}`} className={classes.representationFrame}>
        <div className={classes.frameHeader}>
          <Typography variant={'subtitle1'}>{representation.label}</Typography>
          <IconButton aria-label="remove" onClick={onDelete}>
            <DeleteIcon fontSize="small" />
          </IconButton>
        </div>
        <RepresentationComponent key={`${editingContextId}#${representation.id}`} {...props} />
      </div>
    );
  } else {
    return <div>Unknown representation kind</div>;
  }
};
