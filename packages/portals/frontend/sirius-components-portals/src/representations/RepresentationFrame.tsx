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

import {
  RepresentationComponentProps,
  representationFactoryExtensionPoint,
  useData,
} from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import CloseOutlinedIcon from '@material-ui/icons/CloseOutlined';
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
    position: 'sticky',
    top: 0,
    zIndex: 1,
  },
  title: {
    paddingLeft: theme.spacing(1),
    position: 'sticky',
    left: 0,
  },
  removeIcon: {
    position: 'sticky',
    right: 0,
  },
}));

export const RepresentationFrame = ({
  editingContextId,
  representation,
  portalMode,
  onDelete,
}: RepresentationFrameProps) => {
  const { data: representationFactories } = useData(representationFactoryExtensionPoint);
  const RepresentationComponent = representationFactories
    .map((representationFactory) => representationFactory(representation))
    .find((component) => component != null);

  if (RepresentationComponent) {
    const classes = useFrameStyles();
    const props: RepresentationComponentProps = {
      editingContextId,
      representationId: representation.id,
      readOnly: portalMode === 'edit' || portalMode === 'read-only',
    };

    return (
      <div data-testid={`representation-frame-${representation.label}`} className={classes.representationFrame}>
        <div className={classes.frameHeader} data-testid="representation-frame-header">
          <Typography variant={'subtitle2'} className={classes.title + ' draggable'}>
            {representation.label}
          </Typography>
          {portalMode === 'edit' ? (
            <IconButton
              aria-label="remove"
              className={classes.removeIcon}
              onClick={(e) => {
                e.preventDefault();
                onDelete();
              }}
              size="small">
              <CloseOutlinedIcon fontSize="small" />
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
