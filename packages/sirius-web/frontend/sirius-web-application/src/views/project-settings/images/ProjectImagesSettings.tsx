/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { ImageTable } from './ImageTable';
import { ProjectImagesSettingsParams, ProjectImagesSettingsState } from './ProjectImagesSettings.types';
import { UploadImageModal } from './upload-image/UploadImageModal';
import { useProjectImages } from './useProjectImages';
import { GQLImageMetadata } from './useProjectImages.types';

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
  const [state, setState] = useState<ProjectImagesSettingsState>({
    modal: null,
  });

  const classes = useProjectImagesSettingsStyles();
  const { projectId } = useParams<ProjectImagesSettingsParams>();

  const { data, loading, refreshImages } = useProjectImages(projectId);

  const onTriggerUpload = () => setState((prevState) => ({ ...prevState, modal: 'Upload' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modal: null }));

  const onImageUpdated = () => {
    closeModal();
    refreshImages();
  };

  let main: JSX.Element = null;
  if (!loading && data) {
    const images: GQLImageMetadata[] = data?.viewer.project?.images ?? [];
    if (images.length > 0) {
      main = <ImageTable images={images} onImageUpdated={onImageUpdated} />;
    } else {
      main = (
        <Grid container justifyContent="center">
          <Grid item xs={6}>
            <Typography variant="h6" align="center" gutterBottom>
              No project images available, start by uploading one
            </Typography>
          </Grid>
        </Grid>
      );
    }
  }

  return (
    <>
      <div className={classes.imageSettingsViewContainer}>
        <div className={classes.header}>
          <Typography variant="h4">Project Images</Typography>

          <div className={classes.actions}>
            <Button data-testid="upload-image" color="primary" variant="outlined" onClick={onTriggerUpload}>
              Upload
            </Button>
          </div>
        </div>
        {main}
      </div>
      {state.modal === 'Upload' ? (
        <UploadImageModal projectId={projectId} onImageUploaded={onImageUpdated} onClose={closeModal} />
      ) : null}
    </>
  );
};
