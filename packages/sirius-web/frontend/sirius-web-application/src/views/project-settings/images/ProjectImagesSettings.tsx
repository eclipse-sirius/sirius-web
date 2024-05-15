/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { ProjectSettingTabProps } from '../ProjectSettingsView.types';
import { ImageTable } from './ImageTable';
import { ProjectImagesSettingsParams, ProjectImagesSettingsState } from './ProjectImagesSettings.types';
import { UploadImageModal } from './upload-image/UploadImageModal';
import { useProjectImages } from './useProjectImages';
import { GQLImageMetadata } from './useProjectImages.types';

const useProjectImagesSettingsStyles = makeStyles()((theme) => ({
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

export const ProjectImagesSettings = ({}: ProjectSettingTabProps) => {
  const [state, setState] = useState<ProjectImagesSettingsState>({
    modal: null,
  });

  const { classes } = useProjectImagesSettingsStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'image.list' });
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
          <Grid size={{ xs: 8 }}>
            <Typography variant="h6" align="center" gutterBottom>
              {t('empty')}
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
          <Typography variant="h5">{t('title')}</Typography>

          <div className={classes.actions}>
            <Button data-testid="upload-image" color="primary" variant="outlined" onClick={onTriggerUpload}>
              {t('upload')}
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
