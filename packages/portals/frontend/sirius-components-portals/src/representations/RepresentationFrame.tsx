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
    border: '1px solid',
    borderColor: theme.palette.grey[500],
  },
  frameHeader: {
    backgroundColor: theme.palette.grey[300],
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  title: {
    marginLeft: theme.spacing(1),
    flexGrow: 1,
  },
}));

export const RepresentationFrame = ({
  editingContextId,
  representation,
  readOnly,
  portalMode,
  onDelete,
}: RepresentationFrameProps) => {
  const { registry } = useContext<RepresentationContextValue>(RepresentationContext);
  const RepresentationComponent = registry.getComponent(representation);

  if (RepresentationComponent) {
    const classes = useFrameStyles();
    const props: RepresentationComponentProps = {
      editingContextId,
      readOnly: readOnly || portalMode === 'edit',
      representationId: representation.id,
    };
    return (
      <div data-testid={`representation-frame-${representation.id}`} className={classes.representationFrame}>
        <div className={classes.frameHeader}>
          <Typography variant={'subtitle2'} className={classes.title + ' draggable'}>
            {representation.label}
          </Typography>
          {portalMode === 'edit' ? (
            <IconButton
              aria-label="remove"
              onClick={(e) => {
                e.preventDefault();
                onDelete();
              }}
              size="small">
              <DeleteIcon fontSize="small" />
            </IconButton>
          ) : null}
        </div>
        <RepresentationComponent key={`${editingContextId}#${representation.id}`} {...props} />
      </div>
    );
  } else {
    return <div>Unknown representation kind</div>;
  }
};
