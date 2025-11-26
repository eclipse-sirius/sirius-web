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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { useContext, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLImage } from '../form/FormEventFragments.types';
import { ImageStyleProps } from './ImagePropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useImageStyles = makeStyles<ImageStyleProps>()((_theme, { maxWidth }) => ({
  container: {
    display: 'grid',
    gridTemplateColumns: maxWidth && maxWidth.match(/[0-9]$/) ? `minmax(auto, ${maxWidth}px)` : '1fr',
  },
}));

/**
 * Defines the content of a Image property section.
 */
export const ImagePropertySection: PropertySectionComponent<GQLImage> = ({
  editingContextId,
  formId,
  widget,
}: PropertySectionComponentProps<GQLImage>) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const [validImage, setValidImage] = useState<boolean>(true);
  const { t } = useTranslation('sirius-components-forms', { keyPrefix: 'imagePropertySection' });
  const onErrorLoadingImage = () => {
    setValidImage(false);
  };

  useEffect(() => {
    setValidImage(true);
  }, [widget.url]);

  let imageURL: string;
  if (widget.url.startsWith('http://') || widget.url.startsWith('https://')) {
    imageURL = widget.url;
  } else {
    imageURL = httpOrigin + widget.url;
  }
  const { classes } = useImageStyles({
    maxWidth: widget.maxWidth,
  });

  return (
    <div>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <div className={classes.container}>
        {validImage ? (
          <img id={widget.id} src={imageURL} width="100%" onError={onErrorLoadingImage} />
        ) : (
          <Typography variant="caption">{t('noImage')}</Typography>
        )}
      </div>
    </div>
  );
};
