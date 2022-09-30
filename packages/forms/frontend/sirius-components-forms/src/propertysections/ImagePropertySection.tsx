/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useContext, useEffect, useState } from 'react';
import { ImagePropertySectionProps } from './ImagePropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

interface ImageStylesProps {
  maxWidth: string | null;
}

const useImageStyles = makeStyles<Theme, ImageStylesProps>((_theme) => ({
  container: {
    display: 'grid',
    gridTemplateColumns: ({ maxWidth }) => {
      if (maxWidth) {
        let max = maxWidth;
        if (maxWidth.match(/[0-9]$/)) {
          max = maxWidth + 'px';
        }
        return `minmax(auto, ${max})`;
      } else {
        return '1fr';
      }
    },
  },
}));

/**
 * Defines the content of a Image property section.
 */
export const ImagePropertySection = ({ widget }: ImagePropertySectionProps) => {
  const { httpOrigin } = useContext(ServerContext);
  const [validImage, setValidImage] = useState<boolean>(true);

  const onErrorLoadingImage = () => {
    setValidImage(false);
  };

  useEffect(() => {
    setValidImage(true);
  }, [widget.url]);

  let imageURL;
  if (widget.url.startsWith('http://') || widget.url.startsWith('https://')) {
    imageURL = widget.url;
  } else {
    imageURL = httpOrigin + widget.url;
  }
  const classes = useImageStyles({
    maxWidth: widget.maxWidth,
  });
  return (
    <div>
      <PropertySectionLabel label={widget.label} subscribers={[]} />
      <div className={classes.container}>
        {validImage ? (
          <img id={widget.id} src={imageURL} width="100%" onError={onErrorLoadingImage} />
        ) : (
          <Typography variant="caption">No image</Typography>
        )}
      </div>
    </div>
  );
};
