/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { gql, useQuery } from '@apollo/client';
import { ServerContext, ServerContextValue, Toast } from '@eclipse-sirius/sirius-components-core';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import DeleteIcon from '@material-ui/icons/Delete';
import EditOutlinedIcon from '@material-ui/icons/EditOutlined';
import FileCopyOutlinedIcon from '@material-ui/icons/FileCopyOutlined';
import ImageOutlinedIcon from '@material-ui/icons/ImageOutlined';
import { useMachine } from '@xstate/react';
import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { DeleteImageModal } from '../../modals/delete-image/DeleteImageModal';
import { RenameImageModal } from '../../modals/rename-image/RenameImageModal';
import { UploadImageModal } from '../../modals/upload-image/UploadImageModal';
import {
  GQLCustomImage,
  GQLGetImagesQueryData,
  GQLGetImagesQueryVariables,
  ProjectImagesSettingsParams,
} from './ProjectImagesSettings.types';
import {
  CloseModalEvent,
  FetchedImagesEvent,
  HideToastEvent,
  ImagesSettingsContext,
  ImagesSettingsEvent,
  OpenModalEvent,
  SchemaValue,
  ShowToastEvent,
  imagesViewMachine,
} from './ProjectImagesSettingsMachine';

const getProjectCustomImagesQuery = gql`
  query projectCustomImages($projectId: ID!) {
    viewer {
      project(projectId: $projectId) {
        customImages {
          id
          label
          url
        }
      }
    }
  }
`;

const useProjectImagesSettingsStyles = makeStyles((theme) => ({
  imageSettingsViewContainer: {
    display: 'flex',
    flexDirection: 'column',
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing(5),
  },
  actions: {
    display: 'flex',
    flexDirection: 'row',
    '& > *': {
      marginLeft: theme.spacing(2),
    },
  },
}));

export const ProjectImagesSettings = () => {
  const classes = useProjectImagesSettingsStyles();
  const { projectId } = useParams<ProjectImagesSettingsParams>();
  const [{ value, context }, dispatch] = useMachine<ImagesSettingsContext, ImagesSettingsEvent>(imagesViewMachine);
  const { toast, imagesSettings } = value as SchemaValue;
  const { images, modalToDisplay, currentImage, message } = context;

  const { loading, data, error, refetch } = useQuery<GQLGetImagesQueryData, GQLGetImagesQueryVariables>(
    getProjectCustomImagesQuery,
    {
      variables: {
        projectId,
      },
    }
  );

  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (data) {
        const fetchedImagesEvent: FetchedImagesEvent = { type: 'HANDLE_FETCHED_IMAGES', data };
        dispatch(fetchedImagesEvent);
      }
    }
  }, [loading, data, error, dispatch]);

  const onTriggerUpload = () => dispatch({ type: 'OPEN_MODAL', modalToDisplay: 'Upload' } as OpenModalEvent);

  const triggerRename = (image: GQLCustomImage) => {
    dispatch({ type: 'SELECT_IMAGE', image });
    dispatch({ type: 'OPEN_MODAL', modalToDisplay: 'Rename' } as OpenModalEvent);
  };

  const triggerDelete = (image: GQLCustomImage) => {
    dispatch({ type: 'SELECT_IMAGE', image });
    dispatch({ type: 'OPEN_MODAL', modalToDisplay: 'Delete' } as OpenModalEvent);
  };

  const closeModal = () => {
    dispatch({ type: 'SELECT_IMAGE', image: null });
    dispatch({ type: 'CLOSE_MODAL' } as CloseModalEvent);
  };

  const refreshImages = () => {
    dispatch({ type: 'CLOSE_MODAL' } as CloseModalEvent);
    refetch();
  };

  let modal = null;
  if (modalToDisplay === 'Upload') {
    modal = (
      <UploadImageModal
        projectId={projectId}
        onImageUploaded={() => {
          refreshImages();
          closeModal();
        }}
        onClose={closeModal}
      />
    );
  } else if (modalToDisplay === 'Rename') {
    modal = (
      <RenameImageModal
        imageId={currentImage.id}
        initialImageName={currentImage.label}
        onImageRenamed={() => {
          refreshImages();
          closeModal();
        }}
        onClose={closeModal}
      />
    );
  } else if (modalToDisplay === 'Delete') {
    modal = (
      <DeleteImageModal
        imageId={currentImage.id}
        onImageDeleted={() => {
          refreshImages();
          closeModal();
        }}
        onClose={closeModal}
      />
    );
  }

  let main = <Message content="Loading..." />;
  if (imagesSettings === 'loaded') {
    main = <ImagesTable images={images} onTriggerRename={triggerRename} onTriggerDelete={triggerDelete} />;
  } else if (imagesSettings === 'empty') {
    main = <Message content="No project images available, start by uploading one" />;
  }

  return (
    <>
      <div className={classes.imageSettingsViewContainer}>
        <div className={classes.header}>
          <Typography variant="h3">Project Images</Typography>

          <div className={classes.actions}>
            <Button data-testid="upload-image" color="primary" variant="outlined" onClick={onTriggerUpload}>
              Upload
            </Button>
          </div>
        </div>
        {main}
        {modal}
      </div>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};

interface MessageProps {
  content: string;
}

const Message = ({ content }: MessageProps) => {
  return (
    <Grid container justifyContent="center">
      <Grid item xs={6}>
        <Typography variant="h4" align="center" gutterBottom>
          {content}
        </Typography>
      </Grid>
    </Grid>
  );
};

type ImageTableProps = {
  images: GQLCustomImage[];
  onTriggerRename: (image: GQLCustomImage) => void;
  onTriggerDelete: (image: GQLCustomImage) => void;
};

const ImagesTable = ({ images, onTriggerRename, onTriggerDelete }: ImageTableProps) => {
  return (
    <Paper>
      <TableContainer>
        <Table>
          <colgroup>
            <col width="35%" />
            <col width="60%" />
            <col width="5%" />
          </colgroup>
          <TableHead>
            <TableRow>
              <TableCell variant="head">Label</TableCell>
              <TableCell variant="head">URL</TableCell>
              <TableCell variant="head" align="center">
                Delete
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody data-testid="images">
            {images.map((image) => (
              <ImageRow
                key={image.id}
                image={image}
                onTriggerRename={onTriggerRename}
                onTriggerDelete={onTriggerDelete}
              />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
};

type ImageRowProps = {
  image: GQLCustomImage;
  onTriggerRename: (image: GQLCustomImage) => void;
  onTriggerDelete: (image: GQLCustomImage) => void;
};

const ImagePreviewTooltip = withStyles((theme) => ({
  tooltip: {
    backgroundColor: '#f5f5f5',
    color: 'rgba(0, 0, 0)',
    maxWidth: 220,
    border: '1px solid #dadde9',
  },
}))(Tooltip);

const ImageRow = ({ image, onTriggerRename, onTriggerDelete }: ImageRowProps) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const [showEditIcon, setShowEditIcon] = useState<boolean>(false);

  return (
    <TableRow hover onMouseEnter={() => setShowEditIcon(true)} onMouseLeave={() => setShowEditIcon(false)}>
      <TableCell>
        <Typography>
          {image.label}{' '}
          {showEditIcon ? (
            <IconButton
              onClick={() => {
                onTriggerRename(image);
              }}>
              <EditOutlinedIcon fontSize="small" />
            </IconButton>
          ) : null}
        </Typography>
      </TableCell>
      <TableCell>
        <Typography component="div">
          <Box fontFamily="Monospace" fontSize="small">
            {image.url}{' '}
            <Tooltip title="Copy URL to clipboard">
              <IconButton onClick={() => navigator.clipboard.writeText(image.url)}>
                <FileCopyOutlinedIcon fontSize="small" />
              </IconButton>
            </Tooltip>
            <ImagePreviewTooltip
              enterDelay={250}
              interactive
              title={<img src={httpOrigin + '/api/images' + image.url} width={120} />}>
              <IconButton>
                <ImageOutlinedIcon fontSize="small" />
              </IconButton>
            </ImagePreviewTooltip>
          </Box>
        </Typography>
      </TableCell>
      <TableCell align="center">
        <IconButton
          onClick={() => {
            onTriggerDelete(image);
          }}>
          <DeleteIcon />
        </IconButton>
      </TableCell>
    </TableRow>
  );
};
