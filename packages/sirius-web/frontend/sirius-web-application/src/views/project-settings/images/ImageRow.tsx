/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import Box from '@material-ui/core/Box';
import IconButton from '@material-ui/core/IconButton';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import DeleteIcon from '@material-ui/icons/Delete';
import EditOutlinedIcon from '@material-ui/icons/EditOutlined';
import FileCopyOutlinedIcon from '@material-ui/icons/FileCopyOutlined';
import ImageOutlinedIcon from '@material-ui/icons/ImageOutlined';
import { useContext, useState } from 'react';
import { ImageRowProps, ImageRowState } from './ImageRow.types';
import { DeleteImageModal } from './delete-image/DeleteImageModal';
import { RenameImageModal } from './rename-image/RenameImageModal';

const ImagePreviewTooltip = withStyles(() => ({
  tooltip: {
    backgroundColor: '#f5f5f5',
    color: 'rgba(0, 0, 0)',
    maxWidth: 220,
    border: '1px solid #dadde9',
  },
}))(Tooltip);

export const ImageRow = ({ image, onImageUpdated }: ImageRowProps) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const [state, setState] = useState<ImageRowState>({
    modal: null,
    showEditIcon: false,
  });

  const onRename = () => setState((prevState) => ({ ...prevState, modal: 'Rename' }));
  const onDelete = () => setState((prevState) => ({ ...prevState, modal: 'Delete' }));
  const toggleShowEditIcon = (value: boolean) => setState((prevState) => ({ ...prevState, showEditIcon: value }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modal: null }));

  return (
    <>
      <TableRow hover onMouseEnter={() => toggleShowEditIcon(true)} onMouseLeave={() => toggleShowEditIcon(false)}>
        <TableCell>
          <Typography>
            {image.label}{' '}
            {state.showEditIcon ? (
              <IconButton onClick={onRename}>
                <EditOutlinedIcon fontSize="small" />
              </IconButton>
            ) : null}
          </Typography>
        </TableCell>
        <TableCell>
          <Typography component="div">
            <Box fontFamily="Monospace" fontSize="small">
              {image.id}{' '}
              <Tooltip title="Copy ID to clipboard">
                <IconButton onClick={() => navigator.clipboard.writeText(image.id)}>
                  <FileCopyOutlinedIcon fontSize="small" />
                </IconButton>
              </Tooltip>
              <ImagePreviewTooltip
                enterDelay={250}
                interactive
                title={<img src={httpOrigin + image.url} width={120} />}>
                <IconButton>
                  <ImageOutlinedIcon fontSize="small" />
                </IconButton>
              </ImagePreviewTooltip>
            </Box>
          </Typography>
        </TableCell>
        <TableCell align="center">
          <IconButton onClick={onDelete}>
            <DeleteIcon />
          </IconButton>
        </TableCell>
      </TableRow>
      {state.modal === 'Rename' ? (
        <RenameImageModal
          imageId={image.id}
          initialImageName={image.label}
          onImageRenamed={() => {
            onImageUpdated();
            closeModal();
          }}
          onClose={closeModal}
        />
      ) : null}
      {state.modal === 'Delete' ? (
        <DeleteImageModal
          imageId={image.id}
          onImageDeleted={() => {
            onImageUpdated();
            closeModal();
          }}
          onClose={closeModal}
        />
      ) : null}
    </>
  );
};
